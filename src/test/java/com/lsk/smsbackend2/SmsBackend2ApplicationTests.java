package com.lsk.smsbackend2;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.mapper.NoticeMapper;
import com.lsk.smsbackend2.mapper.StudentMapper;
import com.lsk.smsbackend2.model.Student;
import com.lsk.smsbackend2.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@SpringBootTest
class SmsBackend2ApplicationTests {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Test
    void contextLoads() {
        Page<NoticeMapper.MixedNotice> page = noticeMapper.queryAllNotices(new Page<>(1, 5));
        for (NoticeMapper.MixedNotice notice : page.getRecords()) {
            log.debug(notice.getPublisher());
        }
    }

}
