package com.district.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.district.bean.*;
import com.district.returnJson.ReturnObject;
import com.district.service.EstateService;
import com.district.vo.CellMessage;
import com.district.vo.UnitMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EstateController {

    @Autowired
    private EstateService estateService;

    @RequestMapping("/estate/selectCompany")
    public String selectCompany() {
        System.out.println("selectCompany");
        List<TblCompany> companies = estateService.selectCompany();
        return JSONObject.toJSONString(new ReturnObject(companies));
    }

    @RequestMapping("/estate/insertEstate")
    public String insertEstate(FcEstate fcEstate) {
        System.out.println(fcEstate);
        System.out.println("insert estate");
        Integer result = estateService.insertEstate(fcEstate);
        if (result == 0) {
            return JSONObject.toJSONString(new ReturnObject("0","房产编码已经存在"));
        }else {
            return JSONObject.toJSONString(new ReturnObject("1","插入房产成功"));
        }
    }

    @RequestMapping("/estate/selectBuilding")
    public String selectBuilding(Integer buildingNumber, String estateCode) {
        System.out.println("select building");
        List<FcBuilding> fcBuildings = estateService.selectBuilding(buildingNumber, estateCode);
        System.out.println(fcBuildings);
        return JSON.toJSONString(new ReturnObject(fcBuildings));
    }

    @RequestMapping("/estate/updateBuilding")
    public String updateBuilding(FcBuilding fcBuilding) {
        System.out.println("update building");
        Integer result = estateService.updateBuilding(fcBuilding);
        if (result == 1) {
            return JSONObject.toJSONString(new ReturnObject("更新楼宇成功"));
        }else {
            return JSONObject.toJSONString(new ReturnObject("更新楼宇失败"));
        }
    }

    @RequestMapping("/estate/selectUnit")
    public String selectUnit(@RequestBody UnitMessage[] unitMessages) {//传入楼宇编码和楼宇名称，先插入单元信息，再查询单元信息
        System.out.println("estate selectUnit");
        List<FcUnit> allUnit = new ArrayList<>();
        for (UnitMessage unitMessage : unitMessages) {
            allUnit.addAll(estateService.selectUnit(unitMessage));
        }
        return JSONObject.toJSONString(new ReturnObject(allUnit));
    }

    @RequestMapping("/estate/updateUnit")
    public String updateUnit(FcUnit fcUnit) {
        Integer result = estateService.updateUnit(fcUnit);
        if (result == 1) {
            return JSONObject.toJSONString(new ReturnObject("更新单元成功"));
        }else {
            return JSONObject.toJSONString(new ReturnObject("更新单元失败"));
        }
    }

    @RequestMapping("/estate/insertCell")
    public String insertCell(@RequestBody CellMessage[] cellMessages){
        System.out.println("insert cell");
        List<FcCell> fcCells = estateService.insertCell(cellMessages);
        return JSONObject.toJSONString(new ReturnObject(fcCells));
    }

    @RequestMapping("/estate/selectBuildingByEstate")
    public String selectBuildingByEstate(String estateCode){
        System.out.println("estate selectBuildingName");
        List<FcBuilding> fcBuildings = estateService.selectBuildingByEstate(estateCode);
        System.out.println(fcBuildings);
        return JSONObject.toJSONString(new ReturnObject(fcBuildings));
    }


    @RequestMapping("/estate/selectUnitByBuildingCode")
    public String selectUnitByBuildingCode(String buildingCode){
        System.out.println("select unitName");
        List<FcUnit> fcUnits = estateService.selectUnitByBuildingCode(buildingCode);
        System.out.println(fcUnits);
        return JSONObject.toJSONString(new ReturnObject(fcUnits));
    }

    @RequestMapping("/estate/selectCell")//查看所有楼房
    public String selectCell(String unitCode) {
        System.out.println("select cell");
        List<FcCell> fcCells = estateService.selectCell(unitCode);
        System.out.println("------------------");
        System.out.println(fcCells.size());
        return JSONObject.toJSONString(new ReturnObject(fcCells));
    }

    //楼盘管理/住宅小区/批量增加楼宇/选择住宅
    @RequestMapping("/estate/selectEstate")
    public String selectEstate(String company) {
        System.out.println("estate company");
        List<FcEstate> fcEstates = estateService.selectEstate(company);
        return JSONObject.toJSONString(new ReturnObject(fcEstates));
    }

    @RequestMapping("/estate/selectCountBuilding")
    public String selectCountBuilding(String estateCode){
        Integer integer = estateService.selectCountBuilding(estateCode);
        System.out.println("-------------------"+integer);
        return JSONObject.toJSONString(new ReturnObject(integer));
    }



}
