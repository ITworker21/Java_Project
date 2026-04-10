package com.sky.controller.admin;

import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.dto.DishDTO;
import com.sky.service.DishService;

import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    DishService dishService;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO)
    {
        log.info("新增菜品:{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO)
    {
        log.info("菜品分页查寻{}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("菜品删除")
    public Result delete(@RequestParam List<Long> ids)
    {
        log.info("菜品删除{}",ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }

    @ApiOperation("菜品修改查询")
    @GetMapping("/{id}")
    public Result<DishVO> getById(@RequestParam Long id)
    {
        DishVO dishVO = dishService.getByIdWithFlavors(id);
        return Result.success(dishVO);
    }

    public Result update(DishDTO dishDTO)
    {
        log.info("修改菜品:{}",dishDTO);
        Dish dish =new Dish();
        BeanUtils.copyProperties(dishDTO,dish);


        dishMapper.update(dish);

        dishFlavorMapper.getByDishId(dish.getId());

        List<DishFlavor> dishFlavors = dishDTO.getFlavors();


        if (dishFlavors!=null&&dishFlavors.size()>0)
                dishFlavorMapper.insertBatch(dishFlavors);

        return Result.success();
    }

}
