package com.swsm.linkmes.controller.basic;

import com.swsm.linkmes.model.basic.Dict;
import com.swsm.linkmes.model.basic.DictIdx;
import com.swsm.linkmes.service.basic.IDictService;
import com.swsm.linkmes.vo.basic.DictIndexVo;
import com.swsm.linkmes.vo.basic.DictQueryVo;
import com.swsm.linkmes.vo.basic.DictVo;
import com.swsm.platform.action.BaseAction;
import com.swsm.platform.action.CommonJsonVo;
import com.swsm.platform.action.MultiResultResponse;
import com.swsm.platform.action.UniqueResultResponse;
import com.swsm.platform.model.PageFilter;
import com.swsm.platform.util.converter.entity.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: DictAction
 * @ProjectName mes-sm
 * @Description: DictApi实现类
 * @date 2018/12/2110:00
 */
@RestController
public class DictAction extends BaseAction {

    @Autowired
    private IDictService dictService;

    @Override
    protected boolean isNotFindEntity(String jpql, Map<String, Object> queryMap) {
        return this.dictService.isExistsDict(jpql,queryMap);
    }

    @GetMapping("/basic/dict/getDicts/{start}/{limit}")
    public MultiResultResponse<DictVo> getDicts(@ModelAttribute DictQueryVo dictQueryVo,
                                                @PathVariable("start") int start, @PathVariable("limit") int limit) {
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(dictQueryVo.getDictIndexKey())){
            paramMap.put("parentKey",dictQueryVo.getDictIndexKey());
        }
        PageFilter pageFilter = PageFilter.of(start,limit);
        List<Dict> list = this.dictService.getDictForGrid(paramMap,pageFilter);
        List<DictVo> results = ConvertUtil.toNList(list,Dict.class,DictVo.class);
        return new MultiResultResponse(results,pageFilter.getTotal());
    }

    @PostMapping("/basic/dict/saveOrupdateDicts")
    public UniqueResultResponse<String> saveOrUpdateDicts(@ModelAttribute CommonJsonVo commonJsonVo) {
        List<Dict> list = super.parsorList(commonJsonVo,Dict.class);
        String mes = this.dictService.saveOrUpdateDict(list,commonJsonVo.getUserDisplayName());
        return new UniqueResultResponse<>(mes,mes);
    }

    @DeleteMapping("/basic/dict/deleteDicts/{ids}/{userName}")
    public UniqueResultResponse<String> deletDicts(@PathVariable("ids") String ids,
                                                   @PathVariable("userName") String userName) {
        String msg = this.dictService.deleteDict(ids,userName);
        return new UniqueResultResponse(msg,msg);
    }

    @PostMapping("/basic/dict/checkDictUnique/{uniqueFields}")
    public boolean checkDictUnique(@ModelAttribute CommonJsonVo commonJsonVo,
                                   @PathVariable("uniqueFields") String uniqueFields) {
        return super.queryCheckUnique(uniqueFields.split(","),commonJsonVo, Dict.class);
    }

    @GetMapping("/basic/dict/getDictIndexs/{start}/{limit}")
    public MultiResultResponse<DictIndexVo> getDictIndexs(@ModelAttribute DictQueryVo dictQueryVo,
                                                          @PathVariable("start") int start, @PathVariable("limit") int limit) {
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(dictQueryVo.getDictIndexName())){
            paramMap.put("dictName",dictQueryVo.getDictIndexName());
        }
        if(!StringUtils.isEmpty(dictQueryVo.getDictIndexKey())){
            paramMap.put("dictKey",dictQueryVo.getDictIndexKey());
        }
        if(!StringUtils.isEmpty(dictQueryVo.getDictIndexType()+"")){
            paramMap.put("dictType",dictQueryVo.getDictIndexType());
        }
        PageFilter pageFilter = PageFilter.of(start,limit);
        List<DictIdx> list = this.dictService.getDictIdxForGrid(paramMap,pageFilter);
        List<DictIndexVo> results = ConvertUtil.toNList(list,DictIdx.class,DictIndexVo.class);
        return new MultiResultResponse<>(results,pageFilter.getTotal());
    }



    @PostMapping("/basic/dict/saveOrUpdateSysDictIndexs")
    public UniqueResultResponse<String> saveOrUpdateDictIndexs(@ModelAttribute CommonJsonVo commonJsonVo) {
        List<DictIdx> list = super.parsorList(commonJsonVo,DictIdx.class);
        String mes = this.dictService.saveOrUpdateDictIndex(list,commonJsonVo.getUserDisplayName());
        return new UniqueResultResponse<>(mes,mes);
    }



    @DeleteMapping("/basic/dict/deleteDictIndexs/{ids}/{userName}")
    public UniqueResultResponse<String> deleteDictIndexs(@PathVariable("ids") String ids,@PathVariable("userName") String userName) {
        String msg = this.dictService.deleteDictIndex(ids,userName);
        return new UniqueResultResponse(msg,msg);
    }

    @PostMapping("/basic/dict/checkDictIndexUnique/{uniqueFields}")
    public boolean checkDictIndexUnique(@ModelAttribute CommonJsonVo commonJsonVo,@PathVariable("uniqueFields") String uniqueFields) {
        return super.queryCheckUnique(uniqueFields.split(","),commonJsonVo,DictIndexVo.class);
    }

    @GetMapping("/basic/dict/findDictsByParentKey?parentKey={parentKey}")
    public List<DictVo> findDictsByParentKey(@PathVariable(value = "parentKey",required = false) String parentKey) {
        List<Dict> list =  this.dictService.getDictListyByParentKey(parentKey);
        return ConvertUtil.toNList(list,Dict.class,DictVo.class);
    }
}
