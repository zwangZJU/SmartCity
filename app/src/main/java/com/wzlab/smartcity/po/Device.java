package com.wzlab.smartcity.po;
import java.util.Date;
/**
 * Created by wzlab on 2018/7/10.
 */

public class Device {

        public Device(String user_id, String user_name, String user_address, String status){
                this.user_id = user_id;
                this.user_name = user_name;
                this.user_address = user_address;
                this.status = status;
        }

        /**
         * 序号
         */
        private String id;

        /**
         * 状态
         */
        private String status;

        /**
         * 布撤防
         */
        private String arrange_withdraw;

        /**
         * 用户编号
         */
        private String user_id;

        /**
         * 用户名称
         */
        private String user_name;

        /**
         * 用户地址
         */
        private String user_address;

        /**
         * 分中心
         */
        private String sub_center;

        /**
         * 交叉路
         */
        private String cross_road;

        /**
         * 用户类型
         */
        private String user_type;

        /**
         * 用户类型2
         */
        private String user_type2;

        /**
         * 电话
         */
        private String phone;

        /**
         * 传真
         */
        private String fax;

        /**
         * 主机类型
         */
        private String host_type;

        /**
         * 视频联动
         */
        private String video_linkage;

        /**
         * 负责人
         */
        private String head;

        /**
         * 负责人电话
         */
        private String head_phone;

        /**
         * 测试间隔
         */
        private String test_period;

        /**
         * 缴费截止日期
         */
        private Date pay_date;

        /**
         * 保修截止日期
         */
        private Date guarantee_time;

        /**
         * 核查状态
         */
        private String check_status;

        /**
         * 欠费
         */
        private String arrearage;

        /**
         * 停机
         */
        private String shut_down;

        /**
         * 镇所
         */
        private String town;

        /**
         * 镇所电话
         */
        private String town_phone;

        /**
         * 所属派出所
         */
        private String police_station;

        /**
         * 网络
         */
        private String network;

        /**
         * 派出所电话
         */
        private String police_phone;

        /**
         * 主机位置
         */
        private String host_address;

        /**
         * 安装日期
         */
        private Date insatll_date;

        /**
         * 42代码
         */
        private String code42;

        /**
         * 撤防时间
         */
        private Date withdraw_date;

        /**
         * 布防时间
         */
        private Date arrange_date;

        /**
         * 最后来信号时间
         */
        private Date last_date;

        /**
         * 施工人员
         */
        private String builders;

        /**
         * 出警单位
         */
        private String police_unit;

        /**
         * 主机电话
         */
        private String host_phone;

        /**
         * 预定撤防时间
         */
        private Date prewithdraw_date;

        /**
         * 预定布防时间
         */
        private Date prearrange_date;

        /**
         * 组
         */
        private String group_;

        /**
         * 录入员
         */
        private String entry_clerk;

        /**
         * 巡检人员
         */
        private String inspection_staff;

        /**
         * 短信号码
         */
        private String sms_code;

        /**
         * 停机时间
         */
        private Date downtime;

        /**
         * 电话1
         */
        private String phone1;

        /**
         * 电话2
         */
        private String phone2;

        /**
         * 电话3
         */
        private String phone3;

        /**
         * 负责人电话2
         */
        private String head_phone2;

        /**
         * 入网日期
         */
        private Date net_date;

        /**
         * 通讯线路
         */
        private String communication_line;

        /**
         * 电源位置
         */
        private String power_address;

        /**
         * 电话位置
         */
        private String phone_address;

        /**
         * 键盘位置
         */
        private String keyboard_address;


        /**
         * 序号
         *
         * @return id_
         */
        public String getId_() {
            return id;
        }

        /**
         * 状态
         *
         * @return status
         */
        public String getStatus() {
            return status;
        }

        /**
         * 布撤防
         *
         * @return arrange_withdraw
         */
        public String getArrange_withdraw() {
            return arrange_withdraw;
        }

        /**
         * 用户编号
         *
         * @return userid_
         */
        public String getUserId_() {
            return user_id;
        }

        /**
         * 用户名称
         *
         * @return user_name
         */
        public String getUser_name() {
            return user_name;
        }

        /**
         * 用户地址
         *
         * @return user_address
         */
        public String getUser_address() {
            return user_address;
        }

        /**
         * 分中心
         *
         * @return sub_center
         */
        public String getSub_center() {
            return sub_center;
        }

        /**
         * 交叉路
         *
         * @return cross_road
         */
        public String getCross_road() {
            return cross_road;
        }

        /**
         * 用户类型
         *
         * @return user_type
         */
        public String getUser_type() {
            return user_type;
        }

        /**
         * 用户类型2
         *
         * @return user_type2
         */
        public String getUser_type2() {
            return user_type2;
        }

        /**
         * 电话
         *
         * @return phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         * 传真
         *
         * @return fax
         */
        public String getFax() {
            return fax;
        }

        /**
         * 主机类型
         *
         * @return host_type
         */
        public String getHost_type() {
            return host_type;
        }

        /**
         * 视频联动
         *
         * @return video_linkage
         */
        public String getVideo_linkage() {
            return video_linkage;
        }

        /**
         * 负责人
         *
         * @return head
         */
        public String getHead() {
            return head;
        }

        /**
         * 负责人电话
         *
         * @return head_phone
         */
        public String getHead_phone() {
            return head_phone;
        }

        /**
         * 测试间隔
         *
         * @return test_period
         */
        public String getTest_period() {
            return test_period;
        }

        /**
         * 缴费截止日期
         *
         * @return pay_date
         */
        public Date getPay_date() {
            return pay_date;
        }

        /**
         * 保修截止日期
         *
         * @return guarantee_time
         */
        public Date getGuarantee_time() {
            return guarantee_time;
        }

        /**
         * 核查状态
         *
         * @return check_status
         */
        public String getCheck_status() {
            return check_status;
        }

        /**
         * 欠费
         *
         * @return arrearage
         */
        public String getArrearage() {
            return arrearage;
        }

        /**
         * 停机
         *
         * @return shut_down
         */
        public String getShut_down() {
            return shut_down;
        }

        /**
         * 镇所
         *
         * @return town
         */
        public String getTown() {
            return town;
        }

        /**
         * 镇所电话
         *
         * @return town_phone
         */
        public String getTown_phone() {
            return town_phone;
        }

        /**
         * 所属派出所
         *
         * @return police_station
         */
        public String getPolice_station() {
            return police_station;
        }

        /**
         * 网络
         *
         * @return network
         */
        public String getNetwork() {
            return network;
        }

        /**
         * 派出所电话
         *
         * @return police_phone
         */
        public String getPolice_phone() {
            return police_phone;
        }

        /**
         * 主机位置
         *
         * @return host_address
         */
        public String getHost_address() {
            return host_address;
        }

        /**
         * 安装日期
         *
         * @return insatll_date
         */
        public Date getInsatll_date() {
            return insatll_date;
        }

        /**
         * 42代码
         *
         * @return code42
         */
        public String getCode42() {
            return code42;
        }

        /**
         * 撤防时间
         *
         * @return withdraw_date
         */
        public Date getWithdraw_date() {
            return withdraw_date;
        }

        /**
         * 布防时间
         *
         * @return arrange_date
         */
        public Date getArrange_date() {
            return arrange_date;
        }

        /**
         * 最后来信号时间
         *
         * @return last_date
         */
        public Date getLast_date() {
            return last_date;
        }

        /**
         * 施工人员
         *
         * @return builders
         */
        public String getBuilders() {
            return builders;
        }

        /**
         * 出警单位
         *
         * @return police_unit
         */
        public String getPolice_unit() {
            return police_unit;
        }

        /**
         * 主机电话
         *
         * @return host_phone
         */
        public String getHost_phone() {
            return host_phone;
        }

        /**
         * 预定撤防时间
         *
         * @return prewithdraw_date
         */
        public Date getPrewithdraw_date() {
            return prewithdraw_date;
        }

        /**
         * 预定布防时间
         *
         * @return prearrange_date
         */
        public Date getPrearrange_date() {
            return prearrange_date;
        }

        /**
         * 组
         *
         * @return group_
         */
        public String getGroup_() {
            return group_;
        }

        /**
         * 录入员
         *
         * @return entry_clerk
         */
        public String getEntry_clerk() {
            return entry_clerk;
        }

        /**
         * 巡检人员
         *
         * @return inspection_staff
         */
        public String getInspection_staff() {
            return inspection_staff;
        }

        /**
         * 短信号码
         *
         * @return sms_code
         */
        public String getSms_code() {
            return sms_code;
        }

        /**
         * 停机时间
         *
         * @return downtime
         */
        public Date getDowntime() {
            return downtime;
        }

        /**
         * 电话1
         *
         * @return phone1
         */
        public String getPhone1() {
            return phone1;
        }

        /**
         * 电话2
         *
         * @return phone2
         */
        public String getPhone2() {
            return phone2;
        }

        /**
         * 电话3
         *
         * @return phone3
         */
        public String getPhone3() {
            return phone3;
        }

        /**
         * 负责人电话2
         *
         * @return head_phone2
         */
        public String getHead_phone2() {
            return head_phone2;
        }

        /**
         * 入网日期
         *
         * @return net_date
         */
        public Date getNet_date() {
            return net_date;
        }

        /**
         * 通讯线路
         *
         * @return communication_line
         */
        public String getCommunication_line() {
            return communication_line;
        }

        /**
         * 电源位置
         *
         * @return power_address
         */
        public String getPower_address() {
            return power_address;
        }

        /**
         * 电话位置
         *
         * @return phone_address
         */
        public String getPhone_address() {
            return phone_address;
        }

        /**
         * 键盘位置
         *
         * @return keyboard_address
         */
        public String getKeyboard_address() {
            return keyboard_address;
        }


        /**
         * 序号
         *
         * @param id
         */
        public void setId_(String id) {
            this.id = id;
        }

        /**
         * 状态
         *
         * @param status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * 布撤防
         *
         * @param arrange_withdraw
         */
        public void setArrange_withdraw(String arrange_withdraw) {
            this.arrange_withdraw = arrange_withdraw;
        }

        /**
         * 用户编号
         *
         * @param userId
         */
        public void setUserid_(String userId) {
            this.user_id = userId;
        }

        /**
         * 用户名称
         *
         * @param user_name
         */
        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        /**
         * 用户地址
         *
         * @param user_address
         */
        public void setUser_address(String user_address) {
            this.user_address = user_address;
        }

        /**
         * 分中心
         *
         * @param sub_center
         */
        public void setSub_center(String sub_center) {
            this.sub_center = sub_center;
        }

        /**
         * 交叉路
         *
         * @param cross_road
         */
        public void setCross_road(String cross_road) {
            this.cross_road = cross_road;
        }

        /**
         * 用户类型
         *
         * @param user_type
         */
        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        /**
         * 用户类型2
         *
         * @param user_type2
         */
        public void setUser_type2(String user_type2) {
            this.user_type2 = user_type2;
        }

        /**
         * 电话
         *
         * @param phone
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         * 传真
         *
         * @param fax
         */
        public void setFax(String fax) {
            this.fax = fax;
        }

        /**
         * 主机类型
         *
         * @param host_type
         */
        public void setHost_type(String host_type) {
            this.host_type = host_type;
        }

        /**
         * 视频联动
         *
         * @param video_linkage
         */
        public void setVideo_linkage(String video_linkage) {
            this.video_linkage = video_linkage;
        }

        /**
         * 负责人
         *
         * @param head
         */
        public void setHead(String head) {
            this.head = head;
        }

        /**
         * 负责人电话
         *
         * @param head_phone
         */
        public void setHead_phone(String head_phone) {
            this.head_phone = head_phone;
        }

        /**
         * 测试间隔
         *
         * @param test_period
         */
        public void setTest_period(String test_period) {
            this.test_period = test_period;
        }

        /**
         * 缴费截止日期
         *
         * @param pay_date
         */
        public void setPay_date(Date pay_date) {
            this.pay_date = pay_date;
        }

        /**
         * 保修截止日期
         *
         * @param guarantee_time
         */
        public void setGuarantee_time(Date guarantee_time) {
            this.guarantee_time = guarantee_time;
        }

        /**
         * 核查状态
         *
         * @param check_status
         */
        public void setCheck_status(String check_status) {
            this.check_status = check_status;
        }

        /**
         * 欠费
         *
         * @param arrearage
         */
        public void setArrearage(String arrearage) {
            this.arrearage = arrearage;
        }

        /**
         * 停机
         *
         * @param shut_down
         */
        public void setShut_down(String shut_down) {
            this.shut_down = shut_down;
        }

        /**
         * 镇所
         *
         * @param town
         */
        public void setTown(String town) {
            this.town = town;
        }

        /**
         * 镇所电话
         *
         * @param town_phone
         */
        public void setTown_phone(String town_phone) {
            this.town_phone = town_phone;
        }

        /**
         * 所属派出所
         *
         * @param police_station
         */
        public void setPolice_station(String police_station) {
            this.police_station = police_station;
        }

        /**
         * 网络
         *
         * @param network
         */
        public void setNetwork(String network) {
            this.network = network;
        }

        /**
         * 派出所电话
         *
         * @param police_phone
         */
        public void setPolice_phone(String police_phone) {
            this.police_phone = police_phone;
        }

        /**
         * 主机位置
         *
         * @param host_address
         */
        public void setHost_address(String host_address) {
            this.host_address = host_address;
        }

        /**
         * 安装日期
         *
         * @param insatll_date
         */
        public void setInsatll_date(Date insatll_date) {
            this.insatll_date = insatll_date;
        }

        /**
         * 42代码
         *
         * @param code42
         */
        public void setCode42(String code42) {
            this.code42 = code42;
        }

        /**
         * 撤防时间
         *
         * @param withdraw_date
         */
        public void setWithdraw_date(Date withdraw_date) {
            this.withdraw_date = withdraw_date;
        }

        /**
         * 布防时间
         *
         * @param arrange_date
         */
        public void setArrange_date(Date arrange_date) {
            this.arrange_date = arrange_date;
        }

        /**
         * 最后来信号时间
         *
         * @param last_date
         */
        public void setLast_date(Date last_date) {
            this.last_date = last_date;
        }

        /**
         * 施工人员
         *
         * @param builders
         */
        public void setBuilders(String builders) {
            this.builders = builders;
        }

        /**
         * 出警单位
         *
         * @param police_unit
         */
        public void setPolice_unit(String police_unit) {
            this.police_unit = police_unit;
        }

        /**
         * 主机电话
         *
         * @param host_phone
         */
        public void setHost_phone(String host_phone) {
            this.host_phone = host_phone;
        }

        /**
         * 预定撤防时间
         *
         * @param prewithdraw_date
         */
        public void setPrewithdraw_date(Date prewithdraw_date) {
            this.prewithdraw_date = prewithdraw_date;
        }

        /**
         * 预定布防时间
         *
         * @param prearrange_date
         */
        public void setPrearrange_date(Date prearrange_date) {
            this.prearrange_date = prearrange_date;
        }

        /**
         * 组
         *
         * @param group_
         */
        public void setGroup_(String group_) {
            this.group_ = group_;
        }

        /**
         * 录入员
         *
         * @param entry_clerk
         */
        public void setEntry_clerk(String entry_clerk) {
            this.entry_clerk = entry_clerk;
        }

        /**
         * 巡检人员
         *
         * @param inspection_staff
         */
        public void setInspection_staff(String inspection_staff) {
            this.inspection_staff = inspection_staff;
        }

        /**
         * 短信号码
         *
         * @param sms_code
         */
        public void setSms_code(String sms_code) {
            this.sms_code = sms_code;
        }

        /**
         * 停机时间
         *
         * @param downtime
         */
        public void setDowntime(Date downtime) {
            this.downtime = downtime;
        }

        /**
         * 电话1
         *
         * @param phone1
         */
        public void setPhone1(String phone1) {
            this.phone1 = phone1;
        }

        /**
         * 电话2
         *
         * @param phone2
         */
        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }

        /**
         * 电话3
         *
         * @param phone3
         */
        public void setPhone3(String phone3) {
            this.phone3 = phone3;
        }

        /**
         * 负责人电话2
         *
         * @param head_phone2
         */
        public void setHead_phone2(String head_phone2) {
            this.head_phone2 = head_phone2;
        }

        /**
         * 入网日期
         *
         * @param net_date
         */
        public void setNet_date(Date net_date) {
            this.net_date = net_date;
        }

        /**
         * 通讯线路
         *
         * @param communication_line
         */
        public void setCommunication_line(String communication_line) {
            this.communication_line = communication_line;
        }

        /**
         * 电源位置
         *
         * @param power_address
         */
        public void setPower_address(String power_address) {
            this.power_address = power_address;
        }

        /**
         * 电话位置
         *
         * @param phone_address
         */
        public void setPhone_address(String phone_address) {
            this.phone_address = phone_address;
        }

        /**
         * 键盘位置
         *
         * @param keyboard_address
         */
        public void setKeyboard_address(String keyboard_address) {
            this.keyboard_address = keyboard_address;
        }



}
