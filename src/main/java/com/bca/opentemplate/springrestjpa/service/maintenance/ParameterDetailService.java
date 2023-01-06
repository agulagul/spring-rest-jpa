package com.bca.opentemplate.springrestjpa.service.maintenance;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bca.opentemplate.springrestjpa.model.dao.maintenance.ParameterDetail;
import com.bca.opentemplate.springrestjpa.model.dao.maintenance.ParameterGroup;
import com.bca.opentemplate.springrestjpa.model.dao.maintenance.User;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.ParameterDetailRequestDto;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.ParameterDetailResponseDto;
import com.bca.opentemplate.springrestjpa.repository.maintenance.ParameterDetailRepository;
import com.bca.opentemplate.springrestjpa.repository.maintenance.ParameterGroupRepository;
import com.bca.opentemplate.springrestjpa.repository.maintenance.UserRepository;

@Service
public class ParameterDetailService {
  
  @Autowired
  ParameterDetailRepository parameterDetailRepository;

  @Autowired
  ParameterGroupRepository parameterGroupRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ModelMapper modelMapper;

  public List<ParameterDetailResponseDto> getAllParameterDetail() {
    List<ParameterDetail> parameterDetails = parameterDetailRepository.findAll();
    return parameterDetails.stream().map(e -> modelMapper.map(e, ParameterDetailResponseDto.class)).collect(Collectors.toList());
  }

  public ParameterDetailResponseDto getParameterDetailById(UUID id) {
    ParameterDetail parameterDetail = parameterDetailRepository.findById(id).get();
    return modelMapper.map(parameterDetail, ParameterDetailResponseDto.class);
  }

  public List<ParameterDetailResponseDto> getAllParameterDetailsByCode(ParameterDetailRequestDto parameterDetailRequest) {
    List<ParameterDetail> parameterDetails = parameterDetailRepository.findAllByParamDetailCode(parameterDetailRequest.getParamDetailCode());
    return parameterDetails.stream().map(e -> modelMapper.map(e, ParameterDetailResponseDto.class)).collect(Collectors.toList());
  }

  public ParameterDetailResponseDto createParameterDetail(ParameterDetailRequestDto parameterDetailRequest){
    User user = userRepository.findById(UUID.fromString(parameterDetailRequest.getCreatedBy())).get();
    ParameterGroup parameterGroup = parameterGroupRepository.findById(UUID.fromString(parameterDetailRequest.getParameterGroupId())).get();
    ParameterDetail newParameterDetail = modelMapper.map(parameterDetailRequest, ParameterDetail.class);
    newParameterDetail.setCreatedBy(user);
    newParameterDetail.setParamGroup(parameterGroup);
    ParameterDetail createdParameterDetail = parameterDetailRepository.save(newParameterDetail);
    return modelMapper.map(createdParameterDetail, ParameterDetailResponseDto.class);
  }
  
  public ParameterDetailResponseDto updateParameterDetail(UUID id, ParameterDetailRequestDto parameterDetailRequest){
    User user = userRepository.findById(UUID.fromString(parameterDetailRequest.getUpdatedBy())).get();
    ParameterGroup parameterGroup = parameterGroupRepository.findById(UUID.fromString(parameterDetailRequest.getParameterGroupId())).get();
    ParameterDetail parameterDetail = parameterDetailRepository.findById(id).get();
    modelMapper.map(parameterDetailRequest, parameterDetail);
    parameterDetail.setUpdatedBy(user);
    parameterDetail.setParamGroup(parameterGroup);
    parameterDetailRepository.save(parameterDetail);
    return modelMapper.map(parameterDetail, ParameterDetailResponseDto.class);
  }
  
  public ParameterDetailResponseDto deleteParameterDetail(UUID id){
    ParameterDetail parameterDetail = parameterDetailRepository.findById(id).get();
    parameterDetailRepository.delete(parameterDetail);
    return modelMapper.map(parameterDetail, ParameterDetailResponseDto.class);
  }
}
