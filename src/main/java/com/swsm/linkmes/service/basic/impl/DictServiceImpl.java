package com.swsm.linkmes.service.basic.impl;

import com.swsm.linkmes.domain.repository.DictIdxRepository;
import com.swsm.linkmes.domain.repository.DictRepository;
import com.swsm.linkmes.model.basic.Dict;
import com.swsm.linkmes.model.basic.DictIdx;
import com.swsm.linkmes.service.basic.IDictService;
import com.swsm.platform.model.BaseModelUtil;
import com.swsm.platform.model.PageFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author tinel
 * @Title: DictServiceImpl
 * @ProjectName mes-sm
 * @Description: IDictService服务实现类
 * @date 2018/12/1915:37
 */
@Service
public class DictServiceImpl  implements IDictService {

    @Autowired
    private DictIdxRepository dictIdxRepository;

    @Autowired
    private DictRepository dictRepository;

    @Override
    public List<DictIdx> getDictIdxForGrid(Map<String,Object> paramMap, PageFilter pageFilter) {
        return this.dictRepository.getDictIdxForGrid(paramMap,pageFilter);
    }

    @Override
    public String saveOrUpdateDictIndex(List<DictIdx> objList, String userName) {
        for (DictIdx dictIndex : objList) {
            if (StringUtils.isEmpty(dictIndex.getId())) {
                dictIndex.setDelFlag(BaseModelUtil.DEL_FALSE);
                dictIndex.setCreateDate(new Date());
                dictIndex.setCreateUser(userName);
                this.dictIdxRepository.save(dictIndex);
            } else {
                //更新
                dictIndex.setDelFlag(BaseModelUtil.DEL_FALSE);
                dictIndex.setUpdateDate(new Date());
                dictIndex.setUpdateUser(userName);
                this.dictIdxRepository.update(dictIndex);
            }
        }
        return "success";
    }

    @Override
    public String deleteDictIndex(String ids, String username) {
        for (String id : ids.split(",")) {
            DictIdx dictIdx;
            dictIdx =  this.dictIdxRepository.getById(id);
            //将字典索引下的所有字典也假删除
            List<Dict> dictList;
            dictList = this.dictRepository.deleteDictIndex(dictIdx);
            for (Dict dict : dictList) {
                dict.setDelFlag(BaseModelUtil.DEL_TRUE);
                dict.setUpdateDate(new Date());
                dict.setUpdateUser(username);
                this.dictRepository.update(dict);
            }
            dictIdx.setDelFlag(BaseModelUtil.DEL_TRUE);
            dictIdx.setUpdateDate(new Date());
            dictIdx.setUpdateUser(username);
            this.dictIdxRepository.update(dictIdx);
        }
        return "success";
    }


    @Override
    public List<Dict> getDictForGrid(Map<String, Object> queryMap, PageFilter pageFilter) {
        return this.dictRepository.getDictForGrid(queryMap, pageFilter);
    }

    @Override
    public String saveOrUpdateDict(List<Dict> objList, String userName) {
        for (Dict dict : objList) {
            if (StringUtils.isEmpty(dict.getId())) {
                this.saveDict(dict,userName,BaseModelUtil.DEL_FALSE);
            } else {
                //更新
                this.updateDict(dict,userName,BaseModelUtil.DEL_FALSE);
            }
        }

        return "success";
    }



    @Override
    public String deleteDict(String ids, String userName) {
        for (String id : ids.split(",")) {
            Dict dict = this.dictRepository.getById(id);
            this.updateDict(dict, userName, BaseModelUtil.DEL_TRUE);
        }
        return "success";
    }

    @Override
    public String getDictValueByKey(String parentKey, String dictKey) {
        List<Dict> list = this.dictRepository.getDictValueByKey(parentKey, dictKey);
        if (!list.isEmpty()) {
            return list.get(0).getDictValue();
        }
        return null;
    }

    @Override
    public String getDictKeyByValue(String parentKey, String dictValue) {
        List<Dict> list;
        list = this.dictRepository.getDictKeyByValue(parentKey, dictValue);
        if (!list.isEmpty()) {
            return list.get(0).getDictKey();
        }
        return null;
    }

    @Override
    public List<Dict> getDictListyByParentKey(String parentKey) {
        List<Dict> list;
        list = this.dictRepository.getDictListyByParentKey(parentKey);
        if (!list.isEmpty()) {
            return list;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Dict> getAllBusinessDict() {
        return this.dictRepository.getAllBusinessDict();
    }

    public Dict updateDict(Dict dict,String userName,String delFlag){
        dict.setDelFlag(delFlag);
        dict.setUpdateDate(new Date());
        dict.setUpdateUser(userName);
        this.dictRepository.update(dict);
        return dict;
    }

    /**
     * 保存字典信息
     * @param dict
     * @param userName
     */
    public Dict saveDict(Dict dict,String userName,String delFlag){
        dict.setDelFlag(delFlag);
        dict.setCreateDate(new Date());
        dict.setCreateUser(userName);
        this.dictRepository.save(dict);
        return dict;
    }

    @Override
    public boolean isExistsDict(String jpql, Map<String, Object> paramsMap) {
         List<Dict> list = this.dictRepository.checkExistByJpql(jpql,paramsMap);
         return list.isEmpty();
    }


}
