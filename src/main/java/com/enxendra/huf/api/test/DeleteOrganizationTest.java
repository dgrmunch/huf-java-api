package com.enxendra.huf.api.test;

import com.enxendra.huf.api.Constants;
import com.enxendra.huf.api.RequestOptions;
import com.enxendra.huf.api.model.OrganizationResponse;
import com.enxendra.huf.api.service.OrganizationService;

public class DeleteOrganizationTest extends ServiceTest {

    static RequestOptions requestOptions = new RequestOptions(API_KEY, Constants.API_STR_TEST);

    public static void main(String[] args) {
        OrganizationService service = new OrganizationService(requestOptions);

        try {
            OrganizationResponse organizationResponse = service.deleteOrganization(new Long(359));

            if (organizationResponse.getErrorMessage() != null) {
                System.out.println("ERROR " + organizationResponse.getErrorCode() + ": "
                        + organizationResponse.getErrorMessage());
            } else {
                System.out.println("Se ha efectuado la eliminación satisfactoriamente.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}