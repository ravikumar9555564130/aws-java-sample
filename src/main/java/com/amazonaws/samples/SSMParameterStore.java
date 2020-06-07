package com.amazonaws.samples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;

public class SSMParameterStore {

	private static final String EMS_DEV_DB_PASSWORD = "/ems/dev/db/password";
	private static final String EMS_DEV_DB_USERNAME = "/ems/dev/db/username";
	private static final String EMS_DEV_DB_URL = "/ems/dev/db/url";

	public static void main(String[] args) throws SQLException {

		System.out.println("start SSMParameterStore:: ");

		AWSSimpleSystemsManagement defaultClient = AWSSimpleSystemsManagementClientBuilder.defaultClient();

		GetParametersRequest getParametersRequest = new GetParametersRequest();

		List<String> parametersNames = new ArrayList<String>();
		parametersNames.add(EMS_DEV_DB_USERNAME);
		parametersNames.add(EMS_DEV_DB_URL);
		parametersNames.add(EMS_DEV_DB_PASSWORD);
		getParametersRequest.setNames(parametersNames);

		GetParametersResult parameters = defaultClient.getParameters(getParametersRequest);

		String url = null;
		String userName = null;
		String password = null;

		List<Parameter> parametersDetails = parameters.getParameters();
		for (Parameter parameter : parametersDetails) {

			if (EMS_DEV_DB_URL.equalsIgnoreCase(parameter.getName())) {
				url = parameter.getValue();
				continue;
			} else if (EMS_DEV_DB_USERNAME.equalsIgnoreCase(parameter.getName())) {
				userName = parameter.getValue();
				continue;

			} else if (EMS_DEV_DB_PASSWORD.equalsIgnoreCase(parameter.getName())) {
				password = parameter.getValue();
				continue;

			}

		}

		Connection con = DriverManager.getConnection(
				"jdbc:mysql://employeeooo.ck6bcakdlub5.us-east-1.rds.amazonaws.com/EmployeeOOO", userName, password);

		System.out.println("url :: " + url);
		System.out.println("userName :: " + userName);
		System.out.println("encriptedPassword :: " + password);

		System.out.println("end SSMParameterStore:: ");

	}

}
