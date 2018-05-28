package cn.tties.maint.bean;

import java.util.List;

/**
 * Created by li on 2018/5/28
 * description：
 * author：guojlli
 */

public class EquipmentcheckBean {
    /**
     * result : {"twoCompanyEquipmentItem":[],"threeCompanyEquipmentList":[{"threeCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]}],"twoCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"2#高压开关柜","pid":-1,"companyEquipmentId":531,"eleAccountId":80,"equipmentId":5,"roomId":37}}
     * errorMessage : 成功
     * errorCode : 0
     */

    private ResultBean result;
    private String errorMessage;
    private int errorCode;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public static class ResultBean {
        /**
         * twoCompanyEquipmentItem : []
         * threeCompanyEquipmentList : [{"threeCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]},{"threeCompanyEquipmentItem":[],"threeCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentList":[{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]}]
         * twoCompanyEquipment : {"flag":0,"createTime":"2018-02-01 16:56:10","name":"2#高压开关柜","pid":-1,"companyEquipmentId":531,"eleAccountId":80,"equipmentId":5,"roomId":37}
         */

        private TwoCompanyEquipmentBean twoCompanyEquipment;
        private List<?> twoCompanyEquipmentItem;
        private List<ThreeCompanyEquipmentListBean> threeCompanyEquipmentList;

        public TwoCompanyEquipmentBean getTwoCompanyEquipment() {
            return twoCompanyEquipment;
        }

        public void setTwoCompanyEquipment(TwoCompanyEquipmentBean twoCompanyEquipment) {
            this.twoCompanyEquipment = twoCompanyEquipment;
        }

        public List<?> getTwoCompanyEquipmentItem() {
            return twoCompanyEquipmentItem;
        }

        public void setTwoCompanyEquipmentItem(List<?> twoCompanyEquipmentItem) {
            this.twoCompanyEquipmentItem = twoCompanyEquipmentItem;
        }

        public List<ThreeCompanyEquipmentListBean> getThreeCompanyEquipmentList() {
            return threeCompanyEquipmentList;
        }

        public void setThreeCompanyEquipmentList(List<ThreeCompanyEquipmentListBean> threeCompanyEquipmentList) {
            this.threeCompanyEquipmentList = threeCompanyEquipmentList;
        }

        public static class TwoCompanyEquipmentBean {
            /**
             * flag : 0
             * createTime : 2018-02-01 16:56:10
             * name : 2#高压开关柜
             * pid : -1
             * companyEquipmentId : 531
             * eleAccountId : 80
             * equipmentId : 5
             * roomId : 37
             */

            private int flag;
            private String createTime;
            private String name;
            private int pid;
            private int companyEquipmentId;
            private int eleAccountId;
            private int equipmentId;
            private int roomId;

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getCompanyEquipmentId() {
                return companyEquipmentId;
            }

            public void setCompanyEquipmentId(int companyEquipmentId) {
                this.companyEquipmentId = companyEquipmentId;
            }

            public int getEleAccountId() {
                return eleAccountId;
            }

            public void setEleAccountId(int eleAccountId) {
                this.eleAccountId = eleAccountId;
            }

            public int getEquipmentId() {
                return equipmentId;
            }

            public void setEquipmentId(int equipmentId) {
                this.equipmentId = equipmentId;
            }

            public int getRoomId() {
                return roomId;
            }

            public void setRoomId(int roomId) {
                this.roomId = roomId;
            }
        }

        public static class ThreeCompanyEquipmentListBean {
            /**
             * threeCompanyEquipmentItem : [{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]
             * threeCompanyEquipment : {"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6}
             * fourCompanyEquipmentList : [{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:02:30","name":"1#断路器","pid":531,"companyEquipmentId":534,"eleAccountId":80,"equipmentId":16},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:05","name":"1#隔离刀闸","pid":531,"companyEquipmentId":537,"eleAccountId":80,"equipmentId":8},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":15,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":0,"equipmentItemId":16,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":0,"equipmentItemId":17,"equipmentId":8},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"生产日期","itemOrder":4,"inputType":3,"equipmentItemId":18,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":5,"inputType":0,"equipmentItemId":19,"equipmentId":8},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":6,"inputType":0,"equipmentItemId":20,"equipmentId":8},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:28","name":"1#电流互感器","pid":531,"companyEquipmentId":538,"eleAccountId":80,"equipmentId":12},"fourCompanyEquipmentItem":[{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":26,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定一次电流","unitName":"A","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":27,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"额定二次电流","unitName":"A","itemOrder":3,"defaultValue":"0","inputType":1,"equipmentItemId":28,"equipmentId":12},"equipmentInfo":"0"},{"equipmentItem":{"itemName":"准确等级","itemOrder":4,"inputType":0,"equipmentItemId":29,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":30,"equipmentId":12},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":31,"equipmentId":12},"equipmentInfo":""}]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:03:42","name":"1#电压互感器","pid":531,"companyEquipmentId":539,"eleAccountId":80,"equipmentId":13},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:17","name":"1#继电保护装置","pid":531,"companyEquipmentId":542,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:04:45","name":"1#继电保护装置","pid":531,"companyEquipmentId":549,"eleAccountId":80,"equipmentId":19},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:05:30","name":"1#计量柜","pid":531,"companyEquipmentId":556,"eleAccountId":80,"equipmentId":26},"fourCompanyEquipmentItem":[]},{"fourCompanyEquipment":{"flag":0,"createTime":"2018-02-01 17:07:38","name":"1#联络柜","pid":531,"companyEquipmentId":576,"eleAccountId":80,"equipmentId":68},"fourCompanyEquipmentItem":[]}]
             */

            private ThreeCompanyEquipmentBean threeCompanyEquipment;
            private List<ThreeCompanyEquipmentItemBean> threeCompanyEquipmentItem;
            private List<FourCompanyEquipmentListBean> fourCompanyEquipmentList;

            public ThreeCompanyEquipmentBean getThreeCompanyEquipment() {
                return threeCompanyEquipment;
            }

            public void setThreeCompanyEquipment(ThreeCompanyEquipmentBean threeCompanyEquipment) {
                this.threeCompanyEquipment = threeCompanyEquipment;
            }

            public List<ThreeCompanyEquipmentItemBean> getThreeCompanyEquipmentItem() {
                return threeCompanyEquipmentItem;
            }

            public void setThreeCompanyEquipmentItem(List<ThreeCompanyEquipmentItemBean> threeCompanyEquipmentItem) {
                this.threeCompanyEquipmentItem = threeCompanyEquipmentItem;
            }

            public List<FourCompanyEquipmentListBean> getFourCompanyEquipmentList() {
                return fourCompanyEquipmentList;
            }

            public void setFourCompanyEquipmentList(List<FourCompanyEquipmentListBean> fourCompanyEquipmentList) {
                this.fourCompanyEquipmentList = fourCompanyEquipmentList;
            }

            public static class ThreeCompanyEquipmentBean {
                /**
                 * flag : 0
                 * createTime : 2018-02-01 16:56:10
                 * name : 1#高压开关柜柜体
                 * pid : 531
                 * companyEquipmentId : 532
                 * eleAccountId : 80
                 * equipmentId : 6
                 */

                private int flag;
                private String createTime;
                private String name;
                private int pid;
                private int companyEquipmentId;
                private int eleAccountId;
                private int equipmentId;

                public int getFlag() {
                    return flag;
                }

                public void setFlag(int flag) {
                    this.flag = flag;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getPid() {
                    return pid;
                }

                public void setPid(int pid) {
                    this.pid = pid;
                }

                public int getCompanyEquipmentId() {
                    return companyEquipmentId;
                }

                public void setCompanyEquipmentId(int companyEquipmentId) {
                    this.companyEquipmentId = companyEquipmentId;
                }

                public int getEleAccountId() {
                    return eleAccountId;
                }

                public void setEleAccountId(int eleAccountId) {
                    this.eleAccountId = eleAccountId;
                }

                public int getEquipmentId() {
                    return equipmentId;
                }

                public void setEquipmentId(int equipmentId) {
                    this.equipmentId = equipmentId;
                }
            }

            public static class ThreeCompanyEquipmentItemBean {
                /**
                 * equipmentItem : {"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6}
                 * equipmentInfo :
                 */

                private EquipmentItemBean equipmentItem;
                private String equipmentInfo;

                public EquipmentItemBean getEquipmentItem() {
                    return equipmentItem;
                }

                public void setEquipmentItem(EquipmentItemBean equipmentItem) {
                    this.equipmentItem = equipmentItem;
                }

                public String getEquipmentInfo() {
                    return equipmentInfo;
                }

                public void setEquipmentInfo(String equipmentInfo) {
                    this.equipmentInfo = equipmentInfo;
                }

                public static class EquipmentItemBean {
                    /**
                     * itemName : 型号规格
                     * itemOrder : 1
                     * inputType : 0
                     * equipmentItemId : 7
                     * equipmentId : 6
                     */

                    private String itemName;
                    private int itemOrder;
                    private int inputType;
                    private int equipmentItemId;
                    private int equipmentId;

                    public String getItemName() {
                        return itemName;
                    }

                    public void setItemName(String itemName) {
                        this.itemName = itemName;
                    }

                    public int getItemOrder() {
                        return itemOrder;
                    }

                    public void setItemOrder(int itemOrder) {
                        this.itemOrder = itemOrder;
                    }

                    public int getInputType() {
                        return inputType;
                    }

                    public void setInputType(int inputType) {
                        this.inputType = inputType;
                    }

                    public int getEquipmentItemId() {
                        return equipmentItemId;
                    }

                    public void setEquipmentItemId(int equipmentItemId) {
                        this.equipmentItemId = equipmentItemId;
                    }

                    public int getEquipmentId() {
                        return equipmentId;
                    }

                    public void setEquipmentId(int equipmentId) {
                        this.equipmentId = equipmentId;
                    }
                }
            }

            public static class FourCompanyEquipmentListBean {
                /**
                 * fourCompanyEquipment : {"flag":0,"createTime":"2018-02-01 16:56:10","name":"1#高压开关柜柜体","pid":531,"companyEquipmentId":532,"eleAccountId":80,"equipmentId":6}
                 * fourCompanyEquipmentItem : [{"equipmentItem":{"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"额定电压","unitName":"kV","itemOrder":2,"defaultValue":"0","inputType":1,"equipmentItemId":8,"equipmentId":6},"equipmentInfo":"10"},{"equipmentItem":{"itemName":"标准代号","itemOrder":3,"inputType":0,"equipmentItemId":9,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"出厂编号","itemOrder":4,"inputType":0,"equipmentItemId":10,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"生产日期","itemOrder":5,"inputType":3,"equipmentItemId":11,"equipmentId":6},"equipmentInfo":"2018-2-1"},{"equipmentItem":{"itemName":"生产厂家","itemOrder":6,"inputType":0,"equipmentItemId":12,"equipmentId":6},"equipmentInfo":""},{"equipmentItem":{"itemName":"当前状态","itemOrder":7,"inputType":0,"equipmentItemId":13,"equipmentId":6},"equipmentInfo":""}]
                 */

                private FourCompanyEquipmentBean fourCompanyEquipment;
                private List<FourCompanyEquipmentItemBean> fourCompanyEquipmentItem;

                public FourCompanyEquipmentBean getFourCompanyEquipment() {
                    return fourCompanyEquipment;
                }

                public void setFourCompanyEquipment(FourCompanyEquipmentBean fourCompanyEquipment) {
                    this.fourCompanyEquipment = fourCompanyEquipment;
                }

                public List<FourCompanyEquipmentItemBean> getFourCompanyEquipmentItem() {
                    return fourCompanyEquipmentItem;
                }

                public void setFourCompanyEquipmentItem(List<FourCompanyEquipmentItemBean> fourCompanyEquipmentItem) {
                    this.fourCompanyEquipmentItem = fourCompanyEquipmentItem;
                }

                public static class FourCompanyEquipmentBean {
                    /**
                     * flag : 0
                     * createTime : 2018-02-01 16:56:10
                     * name : 1#高压开关柜柜体
                     * pid : 531
                     * companyEquipmentId : 532
                     * eleAccountId : 80
                     * equipmentId : 6
                     */

                    private int flag;
                    private String createTime;
                    private String name;
                    private int pid;
                    private int companyEquipmentId;
                    private int eleAccountId;
                    private int equipmentId;

                    public int getFlag() {
                        return flag;
                    }

                    public void setFlag(int flag) {
                        this.flag = flag;
                    }

                    public String getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(String createTime) {
                        this.createTime = createTime;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getPid() {
                        return pid;
                    }

                    public void setPid(int pid) {
                        this.pid = pid;
                    }

                    public int getCompanyEquipmentId() {
                        return companyEquipmentId;
                    }

                    public void setCompanyEquipmentId(int companyEquipmentId) {
                        this.companyEquipmentId = companyEquipmentId;
                    }

                    public int getEleAccountId() {
                        return eleAccountId;
                    }

                    public void setEleAccountId(int eleAccountId) {
                        this.eleAccountId = eleAccountId;
                    }

                    public int getEquipmentId() {
                        return equipmentId;
                    }

                    public void setEquipmentId(int equipmentId) {
                        this.equipmentId = equipmentId;
                    }
                }

                public static class FourCompanyEquipmentItemBean {
                    /**
                     * equipmentItem : {"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6}
                     * equipmentInfo :
                     */

                    private EquipmentItemBeanX equipmentItem;
                    private String equipmentInfo;

                    public EquipmentItemBeanX getEquipmentItem() {
                        return equipmentItem;
                    }

                    public void setEquipmentItem(EquipmentItemBeanX equipmentItem) {
                        this.equipmentItem = equipmentItem;
                    }

                    public String getEquipmentInfo() {
                        return equipmentInfo;
                    }

                    public void setEquipmentInfo(String equipmentInfo) {
                        this.equipmentInfo = equipmentInfo;
                    }

                    public static class EquipmentItemBeanX {
                        /**
                         * itemName : 型号规格
                         * itemOrder : 1
                         * inputType : 0
                         * equipmentItemId : 7
                         * equipmentId : 6
                         */

                        private String itemName;
                        private int itemOrder;
                        private int inputType;
                        private int equipmentItemId;
                        private int equipmentId;

                        public String getItemName() {
                            return itemName;
                        }

                        public void setItemName(String itemName) {
                            this.itemName = itemName;
                        }

                        public int getItemOrder() {
                            return itemOrder;
                        }

                        public void setItemOrder(int itemOrder) {
                            this.itemOrder = itemOrder;
                        }

                        public int getInputType() {
                            return inputType;
                        }

                        public void setInputType(int inputType) {
                            this.inputType = inputType;
                        }

                        public int getEquipmentItemId() {
                            return equipmentItemId;
                        }

                        public void setEquipmentItemId(int equipmentItemId) {
                            this.equipmentItemId = equipmentItemId;
                        }

                        public int getEquipmentId() {
                            return equipmentId;
                        }

                        public void setEquipmentId(int equipmentId) {
                            this.equipmentId = equipmentId;
                        }
                    }
                }
            }
        }
    }
}
