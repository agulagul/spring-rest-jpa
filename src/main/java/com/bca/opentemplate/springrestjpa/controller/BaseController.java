package com.bca.opentemplate.springrestjpa.controller;

import com.bca.opentemplate.springrestjpa.configuration.logging.BaseLogging;

public abstract class BaseController extends BaseLogging {

    public static final String API_VERSION = "v1";
    public static final String API_BASE_PATH = "/api/" + API_VERSION;

}
