package com.example.babartrihapsoro.getpluspos.helper;

/**
 * Created by ignat on 16-Jun-16.
 */
public class FixValue
{
// Server Online
	public static final String BASE_URL = "https://mygetplus-staging.azurewebsites.net/mobile/v1/201812/";      // URL to GPI Gateway
	public static final String POS_URL = "https://mygetplus-staging.azurewebsites.net/pos/v1/201812/";      // URL to GPI Gateway

// Server Offline Mobile
//	public static final String BASE_URL = "http://10.10.10.93:3000/mobile/v1/201812/";      // URL to GPI Gateway
//	public static final String BASE_URL = "http://192.168.137.1:3000/mobile/v1/201812/";      // URL to GPI Gateway
//	public static final String BASE_URL = "http://192.168.137.1:8080/mobile/v1/201812/";      // URL to GPI Gateway

// Server Offline POS
//	public static final String POS_URL = "http://10.10.10.93:3000/pos/v1/201812/";      // URL to GPI Gateway
//	public static final String POS_URL = "http://192.168.137.1:3000/pos/v1/201812/";      // URL to GPI Gateway

  // Route path to mobile API
  public static final String RestfulWarehouse = "merchants/brands/{AccountRSN}";

	// Route path to POS API
	public static final String RestfulAccountPOS = "account/login";
	public static final String RestfulSelectMember = "members/membercardnumber";
	public static final String RestTransaksiCash = "transaction/saletransaction";
	public static final String RestTransaksiPoin = "transaction/pointredemption";
	public static final String RestVoucherStatus = "status/voucherstatus";
	public static final String RestVoucherRedeem = "transaction/voucherredemption";
	public static final String RestTransaksiAdjust = "transaction/adjusttransaction";

	//Network Connectivity
	public static final int TimeoutConnection = 45000;
	public static final int TYPE_NONE = 0;
	public static final int TYPE_WIFI = 1;
	public static final int TYPE_MOBILE = 2;

	public static final int intSuccess = 0;
	public static final int intError = -1;
	public static final int intEmpty = -2;

	public static final int SPLASH_DISPLAY_LENGHT = 2000;
	public static final String strNamePref = "id.mygetplus.getpluspos.pref";
}
