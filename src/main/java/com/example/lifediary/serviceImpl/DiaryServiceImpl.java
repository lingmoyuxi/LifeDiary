package com.example.lifediary.serviceImpl;

import com.example.lifediary.entity.Diary;
import com.example.lifediary.mapper.DiaryMapper;
import com.example.lifediary.service.DiaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author MoYuXi
 * @since 2023-02-18
 */
@Service
public class DiaryServiceImpl extends ServiceImpl<DiaryMapper, Diary> implements DiaryService {

}
