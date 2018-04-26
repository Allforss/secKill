package com.sukidesu.server.common.Constants;


import com.sukidesu.server.common.utils.LocalDateTimeUtils;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 常量类
 * @author weixian.yan
 *
 */
public class Constants {

	public static final TimeUnit EXPIRE_TIME_UNIT = TimeUnit.HOURS;

	/**
	 * 空字符串 ""
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * 空Long ""
	 */
	public static final Long EMPTY_LONG = 0L;

	/**
	 * 空Integer ""
	 */
	public static final Integer EMPTY_INTEGER = 0;

	/**
	 * 一个空格的字符串 " "
	 */
	public static final String ONE_SPAN_STRING = " ";

	/**
	 * 默认时间
	 */
	public static final Date DATE_19700101000000 =
			LocalDateTimeUtils.convertLDTToDate(LocalDateTime.of(1970,1,1,0,0,0));



	/**
	 * 各种符号常量
	 */
	public static final class Symbols {

		/**
		 * 冒号和一个空格
		 */
		public static final String COLON_WITH_SPAN = ": ";

		/**
		 * 回车换行 \r\n
		 */
		public static final String LINE_BREAK = "\r\n";

		/**
		 * 横线 -
		 */
		public static final String LINE = "-";

		/**
		 * 下划线 _
		 */
		public static final String UNDERLINE = "_";

		/**
		 * 逗号
		 */
		public static final String COMMA = ",";

		/**
		 * 点号 .
		 */
		public static final String DOT = ".";

		/**
		 * 星号 *
		 */
		public static final String STAR = "*";

		/**
		 * zh
		 */
		public static final String ZH = "zh";


		/**
		 * zh
		 */
		public static final String FILE_SEPARATOR = File.separator;

		/**
		 * 单引号 '
		 */
		public static final String SINGLE_QUOTE_MARK = "'";


		/**
		 * 制表符 \t
		 */
		public static final String TABLE_MARK = "\t";

		/**
		 * 小于号 <
		 */
		public static final String LESS_THAN_MARK = "<";

		/**
		 * 大于号 >
		 */
		public static final String GREATER_THAN_MARK = ">";

		/**
		 * 斜线
		 */
		public static final String SOLIDUS = "/";

	}


	/**
	 * 时间格式字符串常量类
	 */
	public static final class DateFormat {
		/**
		 * yyyy-MM-dd HH:mm:ss 2017-09-09 23:08:30
		 */
		public static final String YYYY_MM_DD_HH24_MI_SS = "yyyy-MM-dd HH:mm:ss";

		/**
		 * yyyy-MM-dd 2017-09-09
		 */
		public static final String YYYY_MM_DD = "yyyy-MM-dd";
		
	    /**
	     * yyyyMMdd 日期格式
	     */
		public static final String YYYYMMDD = "yyyyMMdd";

		/**
		 * yyyyMMddHHmmssSSS 20170911102332102
		 */
		public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

		/**
		 * yyyyMMddHHmmss 201709111023321
		 */
		public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

		/**
		 * yyyy 日期格式
		 */
		public static final String YYYY = "yyyy";


		/**
		 * MM 日期格式
		 */
		public static final String MM = "MM";

	}


	/**
	 * 字符编码常量类
	 */
	public static final class Charset{

		public static final String GBK = "GBK";
		public static final String UTF8 = "UTF-8";
	}


}
