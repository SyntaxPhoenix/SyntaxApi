package com.syntaxphoenix.syntaxapi.net.http;

public final class ResponseCode {
	
	/**
	 *
	 * Informations
	 * 
	 */

	public static final int CONTINUE = 100;
	public static final int SWITCHING_PROTOCOLS = 101;
	public static final int PROCESSING = 102;

	/**
	 *
	 * Succeeded Operations
	 * 
	 */
	
	public static final int OK = 200;
	public static final int CREATED = 201;
	public static final int ACCEPTED = 202;
	public static final int NON_AUTHORITATIVE_INFORMATION = 203;
	public static final int NO_CONTENT = 204;
	public static final int RESET_CONTENT = 205;
	public static final int PARTIAL_CONTENT = 206;
	public static final int MULTI_STATUS = 207;
	public static final int ALREADY_REPORTED = 208;
	public static final int IM_USED = 226;
	
	/**
	 *
	 * Redirects
	 * 
	 */
	
	public static final int MULTIPLE_CHOICES = 300;
	public static final int MOVED_PERMANENTLY = 301;
	public static final int FOUND__MOVE_TEMPORARILLY = 302;
	public static final int SEE_OTHER = 303;
	public static final int NOT_MODIFIED = 304;
	public static final int USE_PROXY = 305;
	public static final int TEMPORARY_REDIRECT = 307;
	public static final int PERMANENT_REDIRECT = 308;
	
	/**
	 *
	 * Client Errors
	 * 
	 */
	
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int PAYMENT_REQUIRED = 402;
	public static final int FORBIDDEN = 403;
	public static final int NOT_FOUND = 404;
	public static final int METHOD_NOT_ALLOWED = 405;
	public static final int NOT_ACCEPTABLE = 406;
	public static final int PROXY_AUTHENTICATION_REQUIRED = 407;
	public static final int REQUEST_TIMOEOUT = 408;
	public static final int CONFLICT = 409;
	public static final int GONE = 410;
	public static final int LENGTH_REQUIRED = 411;
	public static final int PRECONDITION_FAILED = 412;
	public static final int REQUEST_ENTITY_TOO_LARGE = 413;
	public static final int URI_TOO_LONG = 414;
	public static final int UNSUPPORTED_MEDIA_TYPE = 415;
	public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416;
	public static final int EXPECTATION_FAILED = 417;
	public static final int POLICY_NOT_FULFILLED = 420;
	public static final int MISDIRECTED_REQUEST = 421;
	public static final int UNPROCESSABLE_ENTITY = 422;
	public static final int LOCKED = 423;
	public static final int FAILED_DEPENDENCY = 424;
	public static final int UPGRADE_REQUIRED = 426;
	public static final int PRECONDITION_REQUIRED = 428;
	public static final int TOO_MANY_REQUESTS = 429;
	public static final int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;
	public static final int UNAVAILABLE_FOR_LEGAL_REASONS = 451;

}
