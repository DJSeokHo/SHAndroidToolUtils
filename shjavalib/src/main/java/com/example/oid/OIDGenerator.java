package com.example.oid;

/**
 * 객체의 유일성을 부여하기 위하여 유일한 ID를 생성한다.
 * 	ID의 구조 = 총 11자리 = ServerID[1] + 날짜(long)를 64진수로 변환한 문자열[7] + 64진수 3자리로 표현되는 Seq Number[3] 
 * 080624 - 개발당시 64진수로 했던 이유는 long to string 변환시 bit연산이 가능하도록 하기 위함이다. 따라서 문자의 자릿수가
 * 남는다고 64진수법 이상의 진수법을 사용하려고 노력하지 말도록...
 * 080624[변경] - generateSID 함수에 synchronized를 설정하였으며, 가능하면 호출시마다 currentTimeMillis()를 호출하지 않고 시퀀스
 * 를 모두 소진한 후에 시퀀스를 호출하도록 변경함.(기대효과 : 속도향상) - 코어2듀어 2.4G에서 초당 380만개의 SID생성됨
 * 이론적으로 1milisec당 약 26만개, 하루 약 22조개의 ID발급이 가능함.
 * 
 * @author gckim
 * @modify james
 * @version 1.1
 */

public class OIDGenerator
{

		private static final int WAIT_TIME = 1;
		private static final int NUMBER_SCALE = 64;
		private static final int MAX_SEQ = NUMBER_SCALE * NUMBER_SCALE * NUMBER_SCALE;
		private static final OIDGenerator OIDGenerator = new OIDGenerator( System.getProperty( "oid.serverid", "1" ) );

		public OIDGenerator( String serverID )
		{
				setServerID( serverID );
				m_lIssuedTime = System.currentTimeMillis();
		}

		public static String generateOID()
		{
				return OIDGenerator.makeOID();
		}

		private char m_cServerID;
		private long m_lIssuedTime;
		private int m_nSequence = 0;

		public synchronized String makeOID()
		{
				char[] arrID = new char[11];
				if ( m_nSequence != (MAX_SEQ) )
				{
						arrID[0] = m_cServerID;
						char[] issuedTime = Long2String64.convertLongTo64String( m_lIssuedTime );
						for ( int i = 0; i < 7; i++ )
						{
								arrID[i + 1] = issuedTime[i];
						}
						char[] sequence = Long2String64.convertLongTo64String( m_nSequence, 3 );
						for ( int i = 0; i < 3; i++ )
						{
								arrID[i + 8] = sequence[i];
						}
						m_nSequence++;
				}
				else
				{
						m_lIssuedTime = getNewIssuedTime();
						m_nSequence = 0;
						return makeOID();
				}

				return new String( arrID );
		}

		private long getNewIssuedTime()
		{
				long issuedTime = System.currentTimeMillis();
				if ( m_lIssuedTime == issuedTime )
				{
						wait_for_a_while();
						issuedTime = getNewIssuedTime();
				}
				return issuedTime;
		}

		private void wait_for_a_while()
		{
				try
				{
						Thread.sleep( WAIT_TIME );
				}
				catch ( InterruptedException e )
				{
				}
		}

		public void setServerID( String serverID )
		{
				if ( serverID == null )
						throw new RuntimeException( "OIDGenerator:serverID is null" );
				if ( serverID.length() != 1 )
						throw new RuntimeException( "OIDGenerator:serverID has invalid length" );
				m_cServerID = serverID.charAt( 0 );
		}
}
