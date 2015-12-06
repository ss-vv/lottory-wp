package com.unison.lottery.api.protocol.common;

public class SystemStatusKeyNames {
	
	public static class Login{
		public static final String SUCC = "login_succ";
		public static final String FAIL = "login_fail";
		public static final String FAIL_PASSWORD_WRONG= "login_fail_password_wrong";
		public static final String FAIL_LOGIN_NAME_WRONG= "login_fail_login_name_wrong";
		public static final String FAIL_NAME_OR_PASSWORD_BLANK = "login_fail_login_name_or_pawword_wrong";
		public static final String SHOULD_LOGIN = "should_login";
		public static final String SHOULD_REGISTE = "should_registe";
	} 
	
	public static class Registe{
		public static final String SUCC = "registe_succ";
		public static final String FAIL_NOT_NEW_USER = "registe_fail_not_new_user";
		public static final String FAIL_REALNAME_AND_PHONENUMBER_SHOULD_NOT_BLANK = "registe_fail_realname_and_phonenumber_should_not_blank";
		public static final String FAIL_USERNAME_AND_PASSWORD_SHOULD_NOT_BLANK = "registe_fail_username_and_password_should_not_blank";
		public static final String FAIL = "registe_fail";
		public static final String NICKNAME_FAIL = "nickName_Fail";
		public static final String NICKNAME_ILLEGAL = "nickName_illegal";
		public static final String SECURITY_CODE_FAIL = "security_code_fail";
		public static final String REGISTERED = "registered";
		public static final String NOT_ALLOW = "registe_not_allow";
		
	}
	
	public static class QueryImmediateIndexDetails{

		public static final String QUERY_INDEX_DETAILS_SUCC = "request_stauts_succ";
		public static final String QUERY_INDEX_DETAILS_FAIL = "request_stauts_fail";
		
	}
	public static class BindIDCard{
		public static final String SUCC = "bind_id_card_succ";
		public static final String FAIL = "bind_id_card_fail";
		public static final String ID_CARD_HAVE_BINDED = "bind_id_card_fail_id_card_have_binded";
	}
	
	public static class BindBank{
		public static final String SUCC = "bind_bank_succ";
		public static final String FAIL = "bind_bank_fail";
		public static final String FAIL_WITHDRAW_PASSWORD_IS_WRONG = "bind_bank_fail_password_is_wrong";
		public static final String FAIL_WITHDRAW_PASSWORD_IS_BLANK = "bind_bank_fail_password_is_blank";
	}
	public static class QueryOnSaleLottery{
		public static final String SUCC = "query_onsale_lottery_succ";
		public static final String FAIL = "query_onsale_lottery_fail";
	}
	public static class QueryJCZQMatch{
		public static final String SUCC = "query_jczq_match_succ";
		public static final String FAIL = "query_jczq_match_fail";
	}
	public static class QueryJCLQMatch{
		public static final String SUCC = "query_jclq_match_succ";
		public static final String FAIL = "query_jclq_match_fail";
	}
	public static class QueryCTZCMatch {
		public static final String SUCC = "query_ctzc_match_succ";
		public static final String FAIL = "query_ctzc_match_fail";
	}
	public static class BuyHistory{
		public static final String SUCC = "buy_history_succ";
		public static final String FAIL = "buy_history_fail";
	}
	public static class QueryVoucers{
		public static final String SUCC = "query_voucers_succ";
		public static final String FAIL = "query_voucers_fail";
	}
	public static class UseVoucer{
		public static final String SUCC = "use_voucer_succ";
		public static final String FAIL = "use_voucer_fail";
	}
	
	public static class BetScheme{
		public static final String SUCC = "bet_scheme_succ";
		public static final String FAIL = "bet_scheme_fail";
		public static final String ERROR = "bet_scheme_error";
		public static final String OUT_DATE = "bet_scheme_outdate";
		public static final String BALANCE_NOT_ENOUGH = "bet_scheme_balance";
		public static final String ACCOUNT_FROZEN = "bet_scheme_frozen";
		public static final String BET_SCHEME_INVALID_AMOUNT = "bet_scheme_invalid_amount";
		public static final String BET_ISSUE_CLOSE = "bet_issue_close";
		public static final String BET_CLOSE = "bet_close";
		public static final String BET_GROUPBUY_FULL = "bet_scheme_groupbuy_full";
		public static final String BET_LESS_THAN_TOTAL_AMOUNT_LIMIT = "bet_scheme_less_than_total_amount_limit";
		public static final String ALONE_PASSTYPE_LIMIT_TIME = "alone_bet_support_passtype_with_time";
		public static final String GROUP_PASSTYPE_LIMIT_TIME = "group_bet_support_passtype_with_time";
	}
	
	public static class SchemeDetail{
		public static final String SUCC = "scheme_detail_succ";
		public static final String FAIL = "scheme_detail_fail";
		public static final String OUT_DATE = "scheme_detail_out_date";
		public static final String INVALID = "scheme_detail_invalid";
		
	}
	
	public static class SchemeTicket{
		public static final String SUCC = "scheme_ticket_succ";
		public static final String FAIL = "scheme_ticket_fail";
		
	}
	public static class ShowAndFollow{
		public static final String SUCC = "show_and_follow_succ";
		public static final String FAIL = "show_and_follow_fail";
		
	}
	
	public static class GetVerifyCode{
		public static final String SUCC = "get_verify_code_succ";
		public static final String FAIL = "get_verify_code_fail";
		public static final String FAIL_PHONE_NUMBER_IS_BLANK = "get_verify_code_fail_phone_number_is_blank";
		public static final String FAIL_PHONE_NUMBER_IS_NOT_VERIFY = "get_verify_code_fail_phone_number_is_not_verify";
	}
	
	public static class BindMobile{
		public static final String SUCC = "bind_mobile_succ";
		public static final String FAIL = "bind_mobile_fail";
		public static final String FAIL_PHONE_NUMBER_IS_BLANK = "bind_mobile_fail_phone_number_is_blank";
		public static final String FAIL_VERIFY_CODE_IS_WRONG = "bind_mobile_fail_verify_code_is_wrong";
		public static final String FAIL_PHONE_NUMBER_IS_BIND_TO_OTHER = "bind_mobile_fail_phone_number_is_bind_to_other";
		public static final String FAIL_PHONE_NUMBER_IS_WRONG = "bind_mobile_fail_phone_number_is_wrong";
	}


	public static class ModifyPassword{
		public static final String SUCC = "modify_password_succ";
		public static final String FAIL = "modify_password_fail";
		public static final String FAIL_OLD_PASSWORD_IS_WRONG = "modify_password_fail_old_password_is_wrong";
		public static final String FAIL_NEW_PASSWORD_IS_BLANK = "modify_password_fail_new_password_is_blank";
	}
	
	public static class Withdraw{
		public static final String SUCC = "withdraw_succ";
		public static final String FAIL = "withdraw_fail";
		public static final String FAIL_PASSWORD_IS_WRONG = "withdraw_fail_password_is_wrong";
		public static final String FAIL_BANK_NOT_BIND = "withdraw_fail_bank_not_bind";
		public static final String FAIL_AMOUNT_TOO_SMALL = "withdraw_fail_amount_too_small";
		public static final String FAIL_FOUND_NOT_ENOUGH = "withdraw_fail_found_not_enough";
	}
	
	public static class CheckUpdate{

		public static final String NEED_UPDATE = "check_update_succ";
		public static final String NOT_NEED_UPDATE = "check_update_not_need_update";
		
		
	}
	
	public static class SendAdvice{

		public static final String SUCC = "send_advice_succ";
		public static final String FAIL = "send_advice_fail";
		
	}
	
	public static class ActivityNotify{
		public static final String SUCC = "activity_notify_succ";
		public static final String FAIL = "activity_notify_fail";
	}
	
	public static class ForgotPassword{

		public static final String SUCC = "forgot_password_succ";
		public static final String FAIL = "forgot_password_fail";
		public static final String USER_NOT_FOUND = "forgot_password_user_not_found";
		public static final String USER_NOT_BIND_MOBILE = "forgot_password_user_not_bind_mobile";
		public static final String VERIFY_CODE_IS_WRONG = "forgot_password_verify_code_wrong";
		public static final String PHONE_NUMBER_IS_BIND_TO_OTHER = "forgot_password_mobile_bind_to_other";
		
	}
	
	public static class UserDetail{
		public static final String SUCC = "user_detail_succ";
		public static final String FAIL = "user_detail_fail";
	}
	
	public static class LotteryInfo {
		public static final String BONUS_RESULT_SUCC = "bonusResult_succ";
		public static final String BONUS_RESULT_FAIL = "bonusResult_fail";
		
		public static final String LOTTERY_RESULT_SUCC = "lotteryResult_succ";
		public static final String LOTTERY_RESULT_FAIL = "lotteryResult_fail";
	}
	
	public static class SelectFollow {
		public static final String SELECT_FOLLOW_SUCC = "selectFollow_succ";
		public static final String SELECT_FOLLOW_FAIL = "selectFollow_fail";
	}
	
	public static class QueryAlipayRSAKey{
		public static final String SUCC = "query_alipay_rsa_key_succ";
		public static final String FAIL = "query_alipay_rsa_key_fail";
	}
	
	public static class RechargeCard{
		public static final String SUCC = "recharge_card_succ";
		public static final String FAIL = "recharge_card_fail";		
		public static final String SIGNFAILED = "signature_failed_or_unknown_error";
		public static final String PROCESSED = "processed_or_too_frequent";
		public static final String TOMUCH = "card_number_to_much";
		public static final String REPETI = "order_number_repetition";
		public static final String AMOUNTE = "amount_of_payment_error";
		public static final String PAYMENTN = "payment_does_not_open";
		public static final String NOTOB = "not_have_opened_business";
		public static final String CARDE = "card_denomination_error";
		public static final String PASSEM = "password_empty_or_unequal_numbers";
	}
	
	public static class QueryImmediateIndexInfo{
		public static final String QUERY_INDEX_INFO_SUCC = "request_stauts_succ";
		public static final String QUERY_INDEX_INFO_FAIL = "request_stauts_fail";
	}
	public static final String UPLOAD_USER_INFO_SUCC = "upload_userinfo_succ";
	public static final String UPLOAD_USER_INFO_FAIL = "upload_userinfo_fail";
	public static final String GET_USER_INFO_SUCC = "get_user_info_succ";
	public static final String GET_USER_INFO_FAIL = "get_user_info_fail";
	

	
	public static final String RESET_PASSWORD_SUCC = "reset_password_succ";
	public static final String RESET_PASSWORD_FAIL = "reset_password_fail";
	public static final String RESET_PASSWORD_FAIL_PHONENUMBER_INVAILD = "reset_password_fail_phonenumber_invalid";
	public static final String RESET_PASSWORD_FAIL_EMAIL_INVAILD = "reset_password_fail_email_invalid";
	public static final String RESET_PASSWORD_FAIL_TOO_MANY_REQUEST = "reset_password_fail_too_many_request";
	public static final String RESET_PASSWORD_FAIL_PHONENUMBER_NOT_VERIFIED = "reset_password_fail_phonenumber_not_verified";
	public static final String RESET_PASSWORD_FAIL_EMIAL_NOT_VERIFIED = "reset_password_fail_email_not_verified";

	public static final String VERIFY_PHONENUMBER_SUCC = "verify_phonenumber_succ";
	public static final String VERIFY_PHONENUMBER_FAIL = "verify_phonenumber_fail";
	public static final String VERIFY_PHONENUMBER_FAIL_EXPIRED = "verify_phonenumber_fail_expired";
	public static final String VERIFY_PHONENUMBER_FAIL_WRONG_SECURITY_CODE = "verify_phonenumber_fail_wrong_security_code";
	
	
	
	
	public static final String VERIFY_PHONENUMBER_FAIL_INVALID_REQUEST = "verify_phonenumber_fail_invalid_request";
	
	public static final String LOGIN_FAIL = "loginFail";
	
	public static final String QUERY_GROUP_INFO_FAIL = "request_stauts_fail";
	
	public static final String QUERY_GROUP_INFO_SUCC = "request_stauts_succ";
	
	public static final String RESQUEST_PARAM_FAIL = "request_params_fail";
	
	public static final String DATA_FAIL = "data_fail";
	
	public static final String FINISH = "finish";
	
	public static final String NO_FINISH = "noFinish";
	public static class redEnvalope{
		public static final String RED_ENVALOPE_NOT_EXIST = "red_envalope_not_exist";
		public static final String RED_ENVALOPE_GRAB_SUCCESS = "red_envalope_grab_success";
		public static final String RED_ENVALOPE_INVALID = "red_envalope_invalid";
		public static final String GRABED_RED_ENVALOPE = "grabed_red_envalope";
		public static final Object GRANT = "grant";
		public static final Object FUND = "fund";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
