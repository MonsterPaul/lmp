package com.zjht.jfmall.action.admin;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.LayuiResultDto;
import com.zjht.jfmall.common.dto.LayuiResultFailDto;
import com.zjht.jfmall.common.dto.LayuiResultSuccessDto;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.entity.Product;
import com.zjht.jfmall.entity.ProductImg;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.service.ProductImgService;
import com.zjht.jfmall.service.ProductService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 〈兑换商品管理Act〉
 *
 */
@Controller
public class ProductAct {
    @Autowired
    private LogService        logService;
    @Autowired
    private ProductService    productService;
    @Autowired
    private ProductImgService productImgService;


    /**
     * TODO:初始化接收的日期类型不然Date接收不到String类型
     *
     * @param binder
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 兑换商品管理列表页
     *
     * @param request
     * @param modelMap
     * @param product
     * @return
     */
    @RequestMapping(value = "/product/v_list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_list(HttpServletRequest request, ModelMap modelMap, Product product) {
        WebSite.setParameters(request, modelMap);
        if (product == null) {
            product = new Product();
        }
        modelMap.put("product", product);
        return "product/list";
    }

    /**
     * 兑换商品列表数据
     *
     * @param product
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/product/o_list.json", method = {RequestMethod.GET, RequestMethod.POST})
    public LayuiResultDto o_list(Product product, Integer pageNum, Integer pageSize) {
        if (product == null) {
            product = new Product();
        }
        PageInfo<Product> pageInfo = productService.getProductList(product, pageSize, pageNum);
        return new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 进入新增页面
     *
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping(value = "/product/v_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_add(ModelMap modelMap, HttpServletRequest request) {
        //保存请求参数
        WebSite.setParameters(request, modelMap);

        return "product/add";
    }

    /**
     * 新增兑换商品数据
     *
     * @param product
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/product/o_add.do", method = {RequestMethod.GET, RequestMethod.POST})
    public LayuiResultDto o_add(Product product) {
        //校验参数
        checkParams(product);

        productService.insertProduct(product);

        logService.add("新增/兑换商品信息, 兑换商品名称：" + product.getProductName());

        return new LayuiResultSuccessDto("操作成功");
    }

    /**
     * 进入修改页面
     *
     * @param modelMap
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/product/v_update.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_update(ModelMap modelMap, HttpServletRequest request, String id) {
        //保存请求参数
        WebSite.setParameters(request, modelMap);

        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id不能为空");
        }

        Product product = productService.findById(id);
        modelMap.put("product", product);

        //获取对应的商品图片
        getProductImg(modelMap, id);
        return "product/update";
    }


    /**
     * 修改兑换商品数据
     *
     * @param product
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/product/o_update.do", method = {RequestMethod.GET, RequestMethod.POST})
    public LayuiResultDto saveOrUpdate(Product product) {
        //校验参数
        checkParams(product);

        if (productService.updateProduct(product)) {
            logService.add("修改兑换商品信息, 兑换商品名称：" + product.getProductName());
            return new LayuiResultSuccessDto("操作成功");
        } else {
            return new LayuiResultFailDto("操作失败");
        }

    }


    /**
     * 删除商品信息
     */
    @ResponseBody
    @RequestMapping(value = "/product/o_delete.do", method = RequestMethod.POST)
    public LayuiResultDto o_delete(HttpServletRequest request, ModelMap modelMap, String ids) {
        WebSite.setParameters(request, modelMap);
        if (ids == null) {
            return new LayuiResultFailDto();
        }
        productService.deleteById(ids.split(","));
        logService.add("删除兑换商品信息" + ids);
        return new LayuiResultSuccessDto();

    }

    /**
     * 检查参数是否存在  并且是否重复
     *
     * @param product
     */
    private void checkParams(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(product.getId())) {
            map.put("id", product.getId());
        }
        if (StringUtils.isNotBlank(product.getProductCode())) {
            map.put("productCode", product.getProductCode());
            if (productService.productCodeCheck(map)) {
                throw new IllegalArgumentException("该商品编码已存在");
            }
        }


    }

    /**
     * 获取商品ID对应的商品图片数据
     *
     * @param modelMap
     * @param id
     */
    private void getProductImg(ModelMap modelMap, String id) {
        //获取商品ID对应的商品数据
        ProductImg productImg = new ProductImg();
        productImg.setProductId(id);
        List<ProductImg> list              = productImgService.getlist(productImg);
        List<ProductImg> productLogoImgs   = new ArrayList<>();
        List<ProductImg> productDetailImgs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (ProductImg productImg1 : list) {
                //Logo图片
                if (productImg1.getType().equals(String.valueOf(ProductImg.Type.LOGO.getType()))) {
                    productLogoImgs.add(productImg1);
                } else {
                    //详情图片
                    productDetailImgs.add(productImg1);
                }
            }
        }
        modelMap.put("productLogoImg", productLogoImgs.size() > 0 ? productLogoImgs.get(0) : new ProductImg());
        modelMap.put("productDetailImg", productDetailImgs.size() > 0 ? productDetailImgs.get(0) : new ProductImg());

    }
}