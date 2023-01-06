package com.bca.opentemplate.springrestjpa.controller.maintenance;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bca.opentemplate.springrestjpa.controller.BaseController;
import com.bca.opentemplate.springrestjpa.model.dto.eai.EaiResponseDto;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.ParameterDetailRequestDto;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.ParameterDetailResponseDto;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.ParameterGroupRequestDto;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.ParameterGroupResponseDto;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.UserRequestDto;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.UserResponseDto;
import com.bca.opentemplate.springrestjpa.service.maintenance.ParameterDetailService;
import com.bca.opentemplate.springrestjpa.service.maintenance.ParameterGroupService;
import com.bca.opentemplate.springrestjpa.service.maintenance.UserService;
import com.bca.opentemplate.springrestjpa.util.http.HttpResponseMapping;

@RestController
@RequestMapping(BaseController.API_BASE_PATH + "/maintenance")
public class MaintenanceController extends BaseController {
  
  @Autowired
  private UserService userService;
  @Autowired
  private ParameterDetailService parameterDetailService;
  @Autowired
  private ParameterGroupService parameterGroupService;

  @GetMapping("/user")
  public ResponseEntity<EaiResponseDto<List<UserResponseDto>>> getAllUsers() {
    EaiResponseDto<List<UserResponseDto>> eaiResponseDto = new EaiResponseDto<List<UserResponseDto>>(HttpResponseMapping.OK, userService.getAllUsers());
    return ResponseEntity.ok(eaiResponseDto);
  }
  
  @GetMapping("/user/{id}")
  public ResponseEntity<EaiResponseDto<UserResponseDto>> getUserById(@PathVariable("id") String id) {
    EaiResponseDto<UserResponseDto> eaiResponseDto = new EaiResponseDto<UserResponseDto>(HttpResponseMapping.OK, userService.getUserById(UUID.fromString(id)));
    return ResponseEntity.ok(eaiResponseDto);
  }
  
  @PostMapping("/user")
  public ResponseEntity<EaiResponseDto<UserResponseDto>> createUser(@RequestBody UserRequestDto userRequestDto) {
    EaiResponseDto<UserResponseDto> eaiResponseDto = new EaiResponseDto<UserResponseDto>(HttpResponseMapping.OK, userService.createUser(userRequestDto));
    return ResponseEntity.ok(eaiResponseDto);
  }
  
  @PutMapping("/user/{id}")
  public ResponseEntity<EaiResponseDto<UserResponseDto>> updateUser(@PathVariable("id") String id, @RequestBody UserRequestDto userRequestDto) {
    EaiResponseDto<UserResponseDto> eaiResponseDto = new EaiResponseDto<UserResponseDto>(HttpResponseMapping.OK, userService.updateUser(UUID.fromString(id), userRequestDto));
    return ResponseEntity.ok(eaiResponseDto);
  }
  
  @DeleteMapping("/user/{id}")
  public ResponseEntity<EaiResponseDto<UserResponseDto>> deleteUser(@PathVariable("id") String id) {
    EaiResponseDto<UserResponseDto> eaiResponseDto = new EaiResponseDto<UserResponseDto>(HttpResponseMapping.OK, userService.deleteUser(UUID.fromString(id)));
    return ResponseEntity.ok(eaiResponseDto);
  }

  @GetMapping("parameter-detail")
  public ResponseEntity<EaiResponseDto<List<ParameterDetailResponseDto>>> getAllParameters() {
    EaiResponseDto<List<ParameterDetailResponseDto>> eaiResponseDto = new EaiResponseDto<List<ParameterDetailResponseDto>>(HttpResponseMapping.OK, parameterDetailService.getAllParameterDetail());
    return ResponseEntity.ok(eaiResponseDto);
  }
  
  @GetMapping("parameter-detail/{id}")
  public ResponseEntity<EaiResponseDto<ParameterDetailResponseDto>> getParameterById(@PathVariable("id") String id) {
    EaiResponseDto<ParameterDetailResponseDto> eaiResponseDto = new EaiResponseDto<ParameterDetailResponseDto>(HttpResponseMapping.OK, parameterDetailService.getParameterDetailById(UUID.fromString(id)));
    return ResponseEntity.ok(eaiResponseDto);
  }
  
  @PostMapping("parameter-detail")
  public ResponseEntity<EaiResponseDto<ParameterDetailResponseDto>> createParameter(@RequestBody ParameterDetailRequestDto parameterDetailRequestDto) {
    EaiResponseDto<ParameterDetailResponseDto> eaiResponseDto = new EaiResponseDto<ParameterDetailResponseDto>(HttpResponseMapping.OK, parameterDetailService.createParameterDetail(parameterDetailRequestDto));
    return ResponseEntity.ok(eaiResponseDto);
  }

  @PutMapping("parameter-detail/{id}")
  public ResponseEntity<EaiResponseDto<ParameterDetailResponseDto>> updateParameter(@PathVariable("id") String id, @RequestBody ParameterDetailRequestDto parameterDetailRequestDto) {
    EaiResponseDto<ParameterDetailResponseDto> eaiResponseDto = new EaiResponseDto<ParameterDetailResponseDto>(HttpResponseMapping.OK, parameterDetailService.updateParameterDetail(UUID.fromString(id), parameterDetailRequestDto));
    return ResponseEntity.ok(eaiResponseDto);
  }

  @DeleteMapping("parameter-detail/{id}")
  public ResponseEntity<EaiResponseDto<ParameterDetailResponseDto>> deleteParameter(@PathVariable("id") String id) {
    EaiResponseDto<ParameterDetailResponseDto> eaiResponseDto = new EaiResponseDto<ParameterDetailResponseDto>(HttpResponseMapping.OK, parameterDetailService.deleteParameterDetail(UUID.fromString(id)));
    return ResponseEntity.ok(eaiResponseDto);
  }

  @GetMapping("parameter-group")
  public ResponseEntity<EaiResponseDto<List<ParameterGroupResponseDto>>> getAllParameterGroups() {
    EaiResponseDto<List<ParameterGroupResponseDto>> eaiResponseDto = new EaiResponseDto<List<ParameterGroupResponseDto>>(HttpResponseMapping.OK, parameterGroupService.getAllParameterGroups());
    return ResponseEntity.ok(eaiResponseDto);
  }
  
  @GetMapping("parameter-group/{id}")
  public ResponseEntity<EaiResponseDto<ParameterGroupResponseDto>> getParameterGroupById(@PathVariable("id") String id) {
    EaiResponseDto<ParameterGroupResponseDto> eaiResponseDto = new EaiResponseDto<ParameterGroupResponseDto>(HttpResponseMapping.OK, parameterGroupService.getParameterGroupById(UUID.fromString(id)));
    return ResponseEntity.ok(eaiResponseDto);
  }
  
  @PostMapping("parameter-group")
  public ResponseEntity<EaiResponseDto<ParameterGroupResponseDto>> createParameterGroup(@RequestBody ParameterGroupRequestDto parameterGroupRequestDto) {
    EaiResponseDto<ParameterGroupResponseDto> eaiResponseDto = new EaiResponseDto<ParameterGroupResponseDto>(HttpResponseMapping.OK, parameterGroupService.createParameterGroup(parameterGroupRequestDto));
    return ResponseEntity.ok(eaiResponseDto);
  }

  @PutMapping("parameter-group/{id}")
  public ResponseEntity<EaiResponseDto<ParameterGroupResponseDto>> updateParameterGroup(@PathVariable("id") String id, @RequestBody ParameterGroupRequestDto parameterGroupRequestDto) {
    EaiResponseDto<ParameterGroupResponseDto> eaiResponseDto = new EaiResponseDto<ParameterGroupResponseDto>(HttpResponseMapping.OK, parameterGroupService.updateParameterGroup(UUID.fromString(id), parameterGroupRequestDto));
    return ResponseEntity.ok(eaiResponseDto);
  }

  @DeleteMapping("parameter-group/{id}")
  public ResponseEntity<EaiResponseDto<ParameterGroupResponseDto>> deleteParameterGroup(@PathVariable("id") String id) {
    EaiResponseDto<ParameterGroupResponseDto> eaiResponseDto = new EaiResponseDto<ParameterGroupResponseDto>(HttpResponseMapping.OK, parameterGroupService.deleteParameterGroup(UUID.fromString(id)));
    return ResponseEntity.ok(eaiResponseDto);
  }

}
