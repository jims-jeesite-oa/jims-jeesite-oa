package com.thinkgem.jeesite.modules.oa.units;

import com.thinkgem.jeesite.modules.oa.entity.MailInfo;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ComparatorMail implements Comparator {

    public int compare(Object arg0, Object arg1) {
        MailInfo user0 = (MailInfo) arg0;
        MailInfo user1 = (MailInfo) arg1;


        int flag = user1.getTime().compareTo(user0.getTime());
        if (flag == 0) {
            return user1.getName().compareTo(user0.getName());
        } else {
            return flag;
        }
    }
}