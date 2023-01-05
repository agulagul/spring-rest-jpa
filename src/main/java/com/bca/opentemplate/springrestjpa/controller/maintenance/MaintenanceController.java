package com.bca.opentemplate.springrestjpa.controller.maintenance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bca.opentemplate.springrestjpa.controller.BaseController;
import com.bca.opentemplate.springrestjpa.model.dto.eai.EaiResponseDto;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.ParameterDetailResponseDto;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.UserRequestDto;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.UserResponseDto;
import com.bca.opentemplate.springrestjpa.service.maintenance.ParameterDetailService;
import com.bca.opentemplate.springrestjpa.service.maintenance.UserService;
import com.bca.opentemplate.springrestjpa.util.http.HttpResponseMapping;

@RestController
@RequestMapping(BaseController.API_BASE_PATH + "/maintenance")
public class MaintenanceController extends BaseController {
  
  @Autowired
  private UserService userService;
  @Autowired
  private ParameterDetailService parameterDetailService;

  @GetMapping("/user")
  public ResponseEntity<EaiResponseDto<List<UserResponseDto>>> getAllUsers() {
    EaiResponseDto<List<UserResponseDto>> eaiResponseDto = new EaiResponseDto<List<UserResponseDto>>(HttpResponseMapping.OK, userService.getAllUsers());
    return ResponseEntity.ok(eaiResponseDto);
  }
  
  @PostMapping("/user")
  public ResponseEntity<EaiResponseDto<UserResponseDto>> createUser(@RequestBody UserRequestDto userRequestDto) {
    EaiResponseDto<UserResponseDto> eaiResponseDto = new EaiResponseDto<UserResponseDto>(HttpResponseMapping.OK, userService.createUser(userRequestDto));
    return ResponseEntity.ok(eaiResponseDto);
  }

  @GetMapping("parameter-detail")
  public ResponseEntity<EaiResponseDto<List<ParameterDetailResponseDto>>> getAllParameters() {
    EaiResponseDto<List<ParameterDetailResponseDto>> eaiResponseDto = new EaiResponseDto<List<ParameterDetailResponseDto>>(HttpResponseMapping.OK, parameterDetailService.getAllParameterDetail());
    return ResponseEntity.ok(eaiResponseDto);
  }

}
