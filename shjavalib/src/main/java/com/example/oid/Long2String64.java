package com.example.oid;

/**
 * @author gckim
 * @modify james
 * @version 1.0
 */
public class Long2String64
{

		private static char[] simbolic = new char[0];

		static {
				StringBuilder tmp = new StringBuilder();

				// tmp.append( '-' ); // 45

				for ( char ch = '0'; ch <= '9'; ++ch )
						// 48 ~ 57
						tmp.append( ch );

				for ( char ch = 'A'; ch <= 'Z'; ++ch )
						// 65 ~ 90
						tmp.append( ch );

				tmp.append( '_' ); // 95

				for ( char ch = 'a'; ch <= 'z'; ++ch )
						// 97 ~ 122
						tmp.append( ch );

				tmp.append( '~' ); // 126

				simbolic = tmp.toString().toCharArray();
		}

		public static char[] convertLongTo64String( long lValue )
		{
				return convertLongTo64String( lValue, 1 );
		}

		/**
		 * 64진법에 필요한 Bit 필드이다. 0xFFFFFFFFFFFFFFFF // 64bit - 11 byte 0x0FFFFFFFFFFFFFFF // 60bit - 10 byte
		 * 0x003FFFFFFFFFFFFF // 54bit - 09 byte 0x0000FFFFFFFFFFFF // 48bit - 08 byte 0x000003FFFFFFFFFF // 42bit - 07 byte
		 * 0x0000000FFFFFFFFF // 36bit - 06 byte 0x000000003FFFFFFF // 30bit - 05 byte 0x0000000000FFFFFF // 24bit - 04 byte
		 * 0x000000000003FFFF // 18bit - 03 byte 0x0000000000000FFF // 12bit - 02 byte 0x000000000000003F // 6bit - 01 byte
		 * 0x0000000000000000 // 0bit - 00 byte
		 *
		 * @param lValue
		 *
		 * @return
		 */
		public static char[] convertLongTo64String( long lValue, int nPreferredSize )
		{
				// lValue에 대해서 64진수 변환에 필요한 byte의 실제크기를 계산한다.
				int nActualSize = lValue > 0x0FFFFFFFFFFFFFFFL ? 11 : lValue > 0x003FFFFFFFFFFFFFL ? 10 : lValue > 0x0000FFFFFFFFFFFFL ? 9 : lValue > 0x000003FFFFFFFFFFL ? 8 : lValue > 0x0000000FFFFFFFFFL ? 7 : lValue > 0x000000003FFFFFFFL ? 6 : lValue > 0x0000000000FFFFFFL ? 5 : lValue > 0x000000000003FFFFL ? 4 : lValue > 0x0000000000000FFFL ? 3 : lValue > 0x000000000000003FL ? 2 : 1;
				// 선호크기가 실제크기보다 커야 선호크기로 설정이 가능하다. 선호크기가 작으면 무시하고 선호크기만큼으로 결과를 리턴한다.
				if ( nPreferredSize > nActualSize ) {
						nActualSize = nPreferredSize;
				}
				// 만약 실제크기가 11보다 크면 11로 설정한다.
				if ( nActualSize > 11 ) {
						nActualSize = 11;
				}
				// 실제크기만큼 byte버퍼를 생성한다.
				char[] arrBuffer = new char[nActualSize];
				// 6bit만큼 bit shift하여 값을 계산함을 반복한다.
				for ( int i = 0; i < nActualSize; i++ ) {
						arrBuffer[nActualSize - (i + 1)] = simbolic[(int) (lValue & 0x000000000000003F)];
						lValue = lValue >> 6;
				}
				// 결과를 String으로 리턴한다.
				return arrBuffer;
		}
}