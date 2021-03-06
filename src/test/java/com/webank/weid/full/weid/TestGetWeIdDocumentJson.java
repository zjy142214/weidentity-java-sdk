/*
 *       Copyright© (2018) WeBank Co., Ltd.
 *
 *       This file is part of weidentity-java-sdk.
 *
 *       weidentity-java-sdk is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       weidentity-java-sdk is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with weidentity-java-sdk.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.webank.weid.full.weid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.webank.weid.common.BeanUtil;
import com.webank.weid.constant.ErrorCode;
import com.webank.weid.full.TestBaseServcie;
import com.webank.weid.full.TestBaseUtil;
import com.webank.weid.protocol.response.ResponseData;
import mockit.Mock;
import mockit.MockUp;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * getWeIdDocumentJson method for testing WeIdService.
 * 
 * @author v_wbgyang
 *
 */
public class TestGetWeIdDocumentJson extends TestBaseServcie {

    /**
     * case: success.
     *
     * @throws Exception  may be throw Exception
     */
    @Test
    public void testGetWeIdDocumentJsonCase1() throws Exception {

        ResponseData<String> weIdDoc =
            weIdService.getWeIdDocumentJson(createWeIdForGetDoc.getWeId());
        System.out.println("\ngetWeIdDocumentJson result:");
        BeanUtil.print(weIdDoc);

        Assert.assertEquals(ErrorCode.SUCCESS.getCode(), weIdDoc.getErrorCode().intValue());
        Assert.assertNotNull(weIdDoc.getResult());
    }

    /**
     * case: set many times.
     *
     * @throws Exception   may be throw Exception
     */
    @Test
    public void testGetWeIdDocumentJsonCase2() throws Exception {

        super.setPublicKey(createWeIdForGetDoc,
            TestBaseUtil.createEcKeyPair()[0],
            createWeIdNew.getWeId());
        super.setAuthentication(createWeIdForGetDoc,
            TestBaseUtil.createEcKeyPair()[0],
            createWeIdForGetDoc.getWeId());
        super.setService(createWeIdForGetDoc,
            "drivingCardServic1",
            "https://weidentity.webank.com/endpoint/8377465");

        ResponseData<String> weIdDoc =
            weIdService.getWeIdDocumentJson(createWeIdForGetDoc.getWeId());
        System.out.println("\ngetWeIdDocumentJson result:");
        BeanUtil.print(weIdDoc);

        Assert.assertEquals(ErrorCode.SUCCESS.getCode(), weIdDoc.getErrorCode().intValue());
        Assert.assertNotNull(weIdDoc.getResult());
    }

    /**
     * case: weIdentity DID is invalid.
     *
     * @throws Exception may be throw Exception
     */
    @Test
    public void testGetWeIdDocumentJsonCase3() throws Exception {

        ResponseData<String> weIdDoc = weIdService.getWeIdDocumentJson("xxxxxxxxxx");
        System.out.println("\ngetWeIdDocumentJson result:");
        BeanUtil.print(weIdDoc);

        Assert.assertEquals(ErrorCode.WEID_INVALID.getCode(), weIdDoc.getErrorCode().intValue());
        Assert.assertEquals(StringUtils.EMPTY, weIdDoc.getResult());
    }

    /**
     * case: weIdentity DID is null.
     *
     * @throws Exception may be throw Exception
     */
    @Test
    public void testGetWeIdDocumentJsonCase4() throws Exception {

        ResponseData<String> weIdDoc = weIdService.getWeIdDocumentJson(null);
        System.out.println("\ngetWeIdDocumentJson result:");
        BeanUtil.print(weIdDoc);

        Assert.assertEquals(ErrorCode.WEID_INVALID.getCode(), weIdDoc.getErrorCode().intValue());
        Assert.assertEquals(StringUtils.EMPTY, weIdDoc.getResult());
    }

    /**
     * case: weIdentity DID is not exists.
     *
     * @throws Exception may be throw Exception
     */
    @Test
    public void testGetWeIdDocumentJsonCase5() throws Exception {

        ResponseData<String> weIdDoc =
            weIdService.getWeIdDocumentJson("did:weid:0xa1c93e93622c6a0b2f52c90741e0b98ab77385a9");
        System.out.println("\ngetWeIdDocumentJson result:");
        BeanUtil.print(weIdDoc);

        Assert.assertEquals(ErrorCode.WEID_DOES_NOT_EXIST.getCode(),
            weIdDoc.getErrorCode().intValue());
        Assert.assertEquals(StringUtils.EMPTY, weIdDoc.getResult());
    }

    /**
     * case: Simulation throws an Exception when calling the
     *       writerWithDefaultPrettyPrinter method.
     * 
     * @throws Exception may be throw Exception
     */
    @Test
    public void testGetWeIdDocumentJsonCase6() throws Exception {

        MockUp<ObjectMapper> mockTest = new MockUp<ObjectMapper>() {
            @Mock
            public ObjectWriter writerWithDefaultPrettyPrinter() throws Exception {
                throw new Exception("excpetion");
            }
        };

        ResponseData<String> weIdDoc =
            weIdService.getWeIdDocumentJson(createWeIdForGetDoc.getWeId());
        System.out.println("\ngetWeIdDocumentJson result:");
        BeanUtil.print(weIdDoc);

        mockTest.tearDown();

        Assert.assertEquals(ErrorCode.SUCCESS.getCode(), weIdDoc.getErrorCode().intValue());
        Assert.assertEquals(StringUtils.EMPTY, weIdDoc.getResult());
    }
}
