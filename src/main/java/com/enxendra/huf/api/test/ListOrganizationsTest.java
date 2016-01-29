package com.enxendra.huf.api.test;

import com.enxendra.huf.api.Constants;
import com.enxendra.huf.api.RequestOptions;
import com.enxendra.huf.api.model.Organization;
import com.enxendra.huf.api.model.OrganizationListResponse;
import com.enxendra.huf.api.service.OrganizationService;

public class ListOrganizationsTest extends ServiceTest {

    static RequestOptions requestOptions = new RequestOptions(API_KEY, Constants.API_STR_TEST);

    public static void main(String[] args) {
        OrganizationService service = new OrganizationService(requestOptions);

        try {
            OrganizationListResponse organizationListResponse = service.listOrganizations();

            if (organizationListResponse.getData() != null) {
                if (organizationListResponse.getData().getOrganizations() != null
                        && organizationListResponse.getData().getOrganizations().size() > 0) {
                    for (Organization organization : organizationListResponse.getData().getOrganizations()) {
                        System.out.println(organization.getId() + ". " + organization.getName() + " - "
                                + organization.getEmail());
                    }
                } else {
                    System.out.println("Organizations not found");
                }

            } else {
                System.out.println("ERROR " + organizationListResponse.getErrorCode() + ": "
                        + organizationListResponse.getErrorMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}