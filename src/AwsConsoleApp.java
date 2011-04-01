/*
 * Copyright 2010-2011 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;

public class AwsConsoleApp {

    static AmazonSimpleDB sdb;

    private static void init() throws Exception {
        AWSCredentials credentials = new PropertiesCredentials(
                AwsConsoleApp.class.getResourceAsStream("AwsCredentials.properties"));

        sdb = new AmazonSimpleDBClient(credentials);
    }


    public static void main(String[] args) throws Exception {

        init();

        try {
        	CreateDomainRequest domReq = new CreateDomainRequest().withDomainName("User");
        	sdb.createDomain(domReq);
        	
        	BatchPutAttributesRequest attReq = new BatchPutAttributesRequest("User", createSampleData());
        	sdb.batchPutAttributes(attReq);
        }
        catch (AmazonServiceException ase) {
                System.out.println("Caught Exception: " + ase.getMessage());
                System.out.println("Reponse Status Code: " + ase.getStatusCode());
                System.out.println("Error Code: " + ase.getErrorCode());
                System.out.println("Request ID: " + ase.getRequestId());
        }

    }
    
    private static List<ReplaceableItem> createSampleData() {
        List<ReplaceableItem> sampleData = new ArrayList<ReplaceableItem>();

        sampleData.add(new ReplaceableItem("SampleUser").withAttributes(
                new ReplaceableAttribute("Name", "Max Mustermann", true)));
        sampleData.add(new ReplaceableItem("SampleUser2").withAttributes(
                new ReplaceableAttribute("Name", "Sigfried Unroy", true)));

        return sampleData;
    }
}
