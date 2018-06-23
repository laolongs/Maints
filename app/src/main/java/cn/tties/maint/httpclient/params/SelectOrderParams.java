package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 查询工单列表请求参数.
 */
public class SelectOrderParams  extends ClinetRequestParams {
    public static final String INTERFACE = "queryOrderList.do";
    private int staffId;
    private int status;
    private Integer workType;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }
    /**
     * {
     "errorCode": 0,
     "errorMessage": "成功",
     "result": [
     {
     "companyId": 43,
     "createTime": "2018-01-30 19:53:58",
     "flag": 0,
     "fromStaffId": 159,
     "inTime": 0,
     "staffId": 160,
     "status": 0,
     "updateTime": "2018-01-30 19:53:58",
     "workOrderId": 151,
     "workType": 2,
     "xunShiCount": 0
     },
     {
     "companyId": 46,
     "createTime": "2018-01-30 20:01:35",
     "flag": 0,
     "fromStaffId": 159,
     "inTime": 0,
     "staffId": 160,
     "status": 0,
     "updateTime": "2018-01-30 20:01:35",
     "workOrderId": 157,
     "workType": 2,
     "xunShiCount": 0
     },
     {
     "company": {
     "agentName": "123",
     "areaId": "330000",
     "businessName": "123",
     "businessTel": "213",
     "companyAddr": "adar",
     "companyId": 49,
     "companyName": "test3",
     "companyShortName": "tc3",
     "createTime": "2018-02-05 15:26:20",
     "creatorId": 167,
     "districtId": 1,
     "eleAccountList": [],
     "endDate": 20180205,
     "energyCompanyId": 1517815613074,
     "financeName": "2312",
     "financeTel": "32112",
     "industryId": 1,
     "maintStaffId": 160,
     "monthMoney": 123,
     "powerFactor": 0.8,
     "rateId": 4,
     "smsTel": "13466711953",
     "startDate": 20180205,
     "status": 0,
     "techName": "1",
     "techTel": "123",
     "transformerCapacity": 123,
     "transformerCount": 12
     },
     "companyId": 49,
     "createTime": "2018-02-05 15:26:20",
     "flag": 0,
     "fromStaffId": 159,
     "inTime": 0,
     "staffId": 160,
     "status": 0,
     "updateTime": "2018-02-05 15:26:20",
     "workOrderId": 175,
     "workType": 2,
     "xunShiCount": 0
     },
     {
     "company": {
     "agentName": "gg",
     "areaId": "330000",
     "businessName": "hjj",
     "businessTel": "2",
     "companyAddr": "fffff",
     "companyId": 52,
     "companyName": "0310test",
     "companyShortName": "0310test",
     "createTime": "2018-03-10 14:33:26",
     "creatorId": 167,
     "districtId": 1,
     "eleAccountList": [],
     "endDate": 20180310,
     "energyCompanyId": 1520663734381,
     "financeName": "gg",
     "financeTel": "55",
     "industryId": 1,
     "maintStaffId": 160,
     "monthMoney": 4,
     "powerFactor": 0.9,
     "rateId": 1,
     "smsTel": "17501092307",
     "startDate": 20180101,
     "status": 0,
     "techName": "gg",
     "techTel": "55",
     "transformerCapacity": 44,
     "transformerCount": 2
     },
     "companyId": 52,
     "createTime": "2018-03-10 14:33:26",
     "flag": 0,
     "fromStaffId": 159,
     "inTime": 0,
     "staffId": 160,
     "status": 0,
     "updateTime": "2018-03-10 14:33:26",
     "workOrderId": 195,
     "workType": 2,
     "xunShiCount": 0
     },
     {
     "company": {
     "agentName": "dsd",
     "areaId": "330000",
     "businessName": "kang",
     "businessTel": "1",
     "companyAddr": "2323",
     "companyId": 56,
     "companyName": "南洋印染",
     "companyShortName": "南洋",
     "createTime": "2018-02-17 14:57:49",
     "creatorId": 155,
     "districtId": 1,
     "eleAccountList": [],
     "endDate": 20180328,
     "energyCompanyId": 1,
     "financeName": "kang2",
     "financeTel": "2",
     "industryId": 1,
     "maintStaffId": 160,
     "monthMoney": 50,
     "powerFactor": 0.9,
     "rateId": 1,
     "smsTel": "13935467835",
     "startDate": 20180325,
     "status": 0,
     "techName": "kang3",
     "techTel": "3",
     "transformerCapacity": 50,
     "transformerCount": 3
     },
     "companyId": 56,
     "createTime": "2018-06-09 13:56:30",
     "executeTime": "Sat Jun 09 13:56:30 CST 2018",
     "flag": 0,
     "fromStaffId": 160,
     "inTime": 1,
     "staffId": 160,
     "status": 0,
     "updateTime": "2018-06-09 13:56:30",
     "workOrderId": 273,
     "workType": 3,
     "xunShiCount": 1
     },
     {
     "company": {
     "agentName": "aga",
     "areaId": "330000",
     "businessName": "a",
     "businessTel": "231235",
     "companyAddr": "ada1",
     "companyId": 23,
     "companyName": "testcs",
     "companyShortName": "testshortname",
     "createTime": "2018-01-17 12:04:18",
     "creatorId": 167,
     "districtId": 1,
     "eleAccountList": [],
     "endDate": 20180606,
     "energyCompanyId": 1517380460548,
     "financeName": "b",
     "financeTel": "235345",
     "industryId": 1,
     "maintStaffId": 160,
     "monthMoney": 213,
     "powerFactor": 0.8,
     "rateId": 8,
     "smsTel": "13466711953",
     "startDate": 20180116,
     "status": 0,
     "techName": "c",
     "techTel": "26457",
     "transformerCapacity": 23423,
     "transformerCount": 2
     },
     "companyId": 23,
     "createTime": "2018-06-13 14:34:37",
     "flag": 0,
     "inTime": 1,
     "staffId": 160,
     "status": 0,
     "updateTime": "2018-06-13 14:34:37",
     "workOrderId": 281,
     "workType": 3,
     "xunShiCount": 5
     },
     {
     "company": {
     "agentName": "sd",
     "areaId": "330000",
     "businessName": "21sdf",
     "businessTel": "123",
     "companyAddr": "address",
     "companyId": 27,
     "companyName": "testlonganme2",
     "companyShortName": "shortname2",
     "createTime": "2018-01-25 18:27:35",
     "creatorId": 167,
     "districtId": 3,
     "eleAccountList": [],
     "endDate": 20180505,
     "energyCompanyId": 1517903644364,
     "financeName": "sdf",
     "financeTel": "1312",
     "industryId": 3,
     "maintStaffId": 160,
     "monthMoney": 231,
     "powerFactor": 0.8,
     "rateId": 1,
     "smsTel": "13466711195",
     "startDate": 20180115,
     "status": 0,
     "techName": "sdf312",
     "techTel": "1222",
     "transformerCapacity": 21,
     "transformerCount": 23
     },
     "companyId": 27,
     "createTime": "2018-06-13 14:34:38",
     "flag": 0,
     "inTime": 1,
     "staffId": 160,
     "status": 0,
     "updateTime": "2018-06-13 14:34:38",
     "workOrderId": 283,
     "workType": 3,
     "xunShiCount": 2
     },
     {
     "company": {
     "agentName": "kang",
     "areaId": "330000",
     "businessName": "kangweijie",
     "businessTel": "13935467835",
     "companyAddr": "zhejiang",
     "companyId": 29,
     "companyName": "huoyingceshi",
     "companyShortName": "huoying",
     "createTime": "2018-01-28 13:26:58",
     "creatorId": 167,
     "districtId": 1,
     "eleAccountList": [],
     "endDate": 20180131,
     "energyCompanyId": 1517119379916,
     "financeName": "kang2",
     "financeTel": "13935467835",
     "industryId": 1,
     "maintStaffId": 160,
     "monthMoney": 50,
     "powerFactor": 0.9,
     "rateId": 3,
     "smsTel": "13935467835",
     "startDate": 20180122,
     "status": 0,
     "techName": "kang3",
     "techTel": "13935467835",
     "transformerCapacity": 5,
     "transformerCount": 2
     },
     "companyId": 29,
     "createTime": "2018-06-13 14:34:38",
     "flag": 0,
     "inTime": 1,
     "staffId": 160,
     "status": 0,
     "updateTime": "2018-06-13 14:34:38",
     "workOrderId": 284,
     "workType": 3,
     "xunShiCount": 3
     }
     ]
     }
     */
}
