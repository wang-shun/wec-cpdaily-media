package com.wisedu.wec.media.web.controller;

import com.wisedu.wec.media.common.vo.CreateGroupFromSelectedRequestBean;
import com.wisedu.wec.media.common.vo.DeleteGroupMembersRequestBean;
import com.wisedu.wec.media.common.vo.GroupMembersRequestBean;
import com.wisedu.wec.media.common.vo.UpdateGroupNameRequestBean;
import com.wisedu.wec.media.biz.service.GroupService;
import com.wisedu.wecloud.commons.model.ResponseResult;
import com.wisedu.wecloud.commons.validator.FieldValidateError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
public class GroupController {

	@Autowired
	private GroupService service;

	@RequestMapping(value = "/group/members/add", method = RequestMethod.POST,
			produces = { "application/json" },
			consumes = { "application/json" })
    public ResponseResult<?> addGroupMembers(@Valid @RequestBody GroupMembersRequestBean groupMembers, BindingResult bindingResult) {
        // do some magic!
        if (bindingResult.hasErrors()) {
    		List<ObjectError> allErrors = bindingResult.getAllErrors();
    		List<FieldValidateError> fieldValidateErrors = new ArrayList<FieldValidateError>(allErrors.size());
    		FieldValidateError fieldValidateError = null;
    		for (ObjectError objectError : allErrors) {
    			FieldError fieldError = (FieldError) objectError;
    			fieldValidateError = new FieldValidateError();
    			fieldValidateError.setField(fieldError.getField());
    			fieldValidateError.setMessage(fieldError.getDefaultMessage());
    			fieldValidateError.setValidator(fieldError.getCode());
    			fieldValidateErrors.add(fieldValidateError);
    		}
    		return ResponseResult.failure(fieldValidateErrors, "Invalid Parameter");
    	}
        return service.addGroupMembers(groupMembers);
    }

	@RequestMapping(value = "/group/create/fromselected", method = RequestMethod.POST,
			produces = { "application/json" },
			consumes = { "application/json" })
    public ResponseResult<?> createGroupFromSelected(@Valid @RequestBody CreateGroupFromSelectedRequestBean groupMembers, BindingResult bindingResult) {
        // do some magic!
        if (bindingResult.hasErrors()) {
    		List<ObjectError> allErrors = bindingResult.getAllErrors();
    		List<FieldValidateError> fieldValidateErrors = new ArrayList<FieldValidateError>(allErrors.size());
    		FieldValidateError fieldValidateError = null;
    		for (ObjectError objectError : allErrors) {
    			FieldError fieldError = (FieldError) objectError;
    			fieldValidateError = new FieldValidateError();
    			fieldValidateError.setField(fieldError.getField());
    			fieldValidateError.setMessage(fieldError.getDefaultMessage());
    			fieldValidateError.setValidator(fieldError.getCode());
    			fieldValidateErrors.add(fieldValidateError);
    		}
    		return ResponseResult.failure(fieldValidateErrors, "Invalid Parameter");
    	}
        return service.createGroupFromSelected(groupMembers);
    }

	@RequestMapping(value = "/group/members/delete", method = RequestMethod.POST,
			produces = { "application/json" },
			consumes = { "application/json" })
    public ResponseResult<?> deleteGroupMembers(@Valid @RequestBody DeleteGroupMembersRequestBean groupMembers, BindingResult bindingResult) {
        // do some magic!
        if (bindingResult.hasErrors()) {
    		List<ObjectError> allErrors = bindingResult.getAllErrors();
    		List<FieldValidateError> fieldValidateErrors = new ArrayList<FieldValidateError>(allErrors.size());
    		FieldValidateError fieldValidateError = null;
    		for (ObjectError objectError : allErrors) {
    			FieldError fieldError = (FieldError) objectError;
    			fieldValidateError = new FieldValidateError();
    			fieldValidateError.setField(fieldError.getField());
    			fieldValidateError.setMessage(fieldError.getDefaultMessage());
    			fieldValidateError.setValidator(fieldError.getCode());
    			fieldValidateErrors.add(fieldValidateError);
    		}
    		return ResponseResult.failure(fieldValidateErrors, "Invalid Parameter");
    	}
        return service.deleteGroupMembers(groupMembers);
    }

	@RequestMapping(value = "/group/name/update", method = RequestMethod.POST,
			produces = { "application/json" },
			consumes = { "application/json" })
    public ResponseResult<?> updateGroupName(@Valid @RequestBody UpdateGroupNameRequestBean groupName, BindingResult bindingResult) {
        // do some magic!
        if (bindingResult.hasErrors()) {
    		List<ObjectError> allErrors = bindingResult.getAllErrors();
    		List<FieldValidateError> fieldValidateErrors = new ArrayList<FieldValidateError>(allErrors.size());
    		FieldValidateError fieldValidateError = null;
    		for (ObjectError objectError : allErrors) {
    			FieldError fieldError = (FieldError) objectError;
    			fieldValidateError = new FieldValidateError();
    			fieldValidateError.setField(fieldError.getField());
    			fieldValidateError.setMessage(fieldError.getDefaultMessage());
    			fieldValidateError.setValidator(fieldError.getCode());
    			fieldValidateErrors.add(fieldValidateError);
    		}
    		return ResponseResult.failure(fieldValidateErrors, "Invalid Parameter");
    	}
        return service.updateGroupName(groupName);
    }

}
