package com.example.ecommerce_a.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

/**
 * 変換系のメソッド集.
 * 
 * @author taka
 *
 */
public class ConvertUtils {

	/**
	 * 郵便番号からハイフンを削除する.
	 * 
	 * @return zipCode 郵便番号
	 */
	public static String getDelHyphenZipCode(String zipCode) {
		StringBuffer hyphenZipCode = new StringBuffer(zipCode);
		int hyphenIndex = hyphenZipCode.indexOf("-");
		if (hyphenIndex == -1) {
			return zipCode;
		} else {
			hyphenZipCode.deleteCharAt(3);
			return hyphenZipCode.toString();
		}
	}

	/**
	 * 電話番号からハイフンを削除する.
	 * 
	 * @param telephone 電話番号
	 * @return
	 */
	public static String getDelHyphenTelephone(String telephone) {
		StringBuffer hyphenTelephone = new StringBuffer(telephone);
		boolean isCheck = true;
		while (isCheck) {
			int hyphenIndex = hyphenTelephone.indexOf("-");
			if (hyphenIndex == -1) {
				return hyphenTelephone.toString();
			} else {
				hyphenTelephone.deleteCharAt(hyphenIndex);
			}
		}
		return null;
	}

	/**
	 * 電話番号にハイフンをつける.
	 * 
	 * @param telephone 電話番号
	 * @return ハイフンをつけた電話番号
	 */
	public static String getHypehnTelephone(String telephone) {
		final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();
		try {
			Phonenumber.PhoneNumber phoneNumber = PHONE_NUMBER_UTIL.parse(telephone, "JP");
			return PHONE_NUMBER_UTIL.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
		} catch (NumberParseException e) {
			return telephone;
		}
	}

}
