package com.lsk.smsbackend2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsk.smsbackend2.model.PayRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PayRecordMapper extends BaseMapper<PayRecord> {
}
