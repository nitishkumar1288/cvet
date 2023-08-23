package com.fluentcommerce.service.product;

import com.fluentcommerce.graphql.query.product.GetVariantProductsQuery;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.fluentcommerce.util.Constants.*;


@Slf4j
public class ProductService {
    private Context context;

    public ProductService(Context context) {
        this.context = context;
    }
    /**
     * @param variantProductList  List of product ref details reference
     * @param refList   list of the product ref data
     * @param catalogueRef product catalogue of ref
     * @param networkCursor cursor id
     * @return Locations List of network data of the order
     */
    public List<GetVariantProductsQuery.Edge> getVariantProductDetailsByRefs(List<GetVariantProductsQuery.Edge> variantProductList,
                                                           List<String> refList,
                                                           String catalogueRef,
                                                           String networkCursor) {
        GetVariantProductsQuery.Builder variantProductRefsBuilder = GetVariantProductsQuery.builder()
                .refs(refList)
                .productCatalogueRef(catalogueRef)
                .productCount(DEFAULT_PAGINATION_PAGE_SIZE);
        if (StringUtils.isNotBlank(networkCursor))
            variantProductRefsBuilder.variantProductCursor(networkCursor).build();

        GetVariantProductsQuery.Data productData = (GetVariantProductsQuery.Data) context.api().query(variantProductRefsBuilder.build());

        if (productData != null && Objects.requireNonNull(productData.variantProducts() != null)
                                && CollectionUtils.isNotEmpty(productData.variantProducts().edges())) {
            List<GetVariantProductsQuery.Edge> productEdgeList = productData.variantProducts().edges();
            assert productEdgeList != null;
            variantProductList.addAll(productEdgeList);
            boolean hasNextPage = productData.variantProducts().pageInfo().hasNextPage();
            if (hasNextPage) {
                String lastCursor = productData.variantProducts().edges().get(productData.variantProducts().edges().size() - 1).cursor();
                getVariantProductDetailsByRefs(variantProductList, refList, catalogueRef, lastCursor);
            }
        }
        return variantProductList;
    }

    private HashMap<String, GetVariantProductsQuery.Node> getVariantProductDetailsByRefsMap(HashMap<String, GetVariantProductsQuery.Node> variantProductDetailsToRefMap,
                                                                                            List<String> refList,
                                                                                            String catalogueRef,
                                                                                            String networkCursor){
        GetVariantProductsQuery.Builder variantProductRefsBuilder = GetVariantProductsQuery.builder()
                .refs(refList)
                .productCatalogueRef(catalogueRef)
                .productCount(DEFAULT_PAGINATION_PAGE_SIZE);
        if (StringUtils.isNotBlank(networkCursor))
            variantProductRefsBuilder.variantProductCursor(networkCursor).build();

        GetVariantProductsQuery.Data productData = (GetVariantProductsQuery.Data) context.api().query(variantProductRefsBuilder.build());

        if (productData != null && Objects.requireNonNull(productData.variantProducts() != null)
                && CollectionUtils.isNotEmpty(productData.variantProducts().edges())) {
            List<GetVariantProductsQuery.Edge> productEdgeList = productData.variantProducts().edges();
            HashMap<String, GetVariantProductsQuery.Node> productDetailsToRefMap = new HashMap<>();
            productEdgeList.forEach(product -> productDetailsToRefMap.put(product.node().ref(), product.node()));
            variantProductDetailsToRefMap.putAll(productDetailsToRefMap);
            boolean hasNextPage = productData.variantProducts().pageInfo().hasNextPage();
            if (hasNextPage) {
                String lastCursor = productData.variantProducts().edges().get(productData.variantProducts().edges().size() - 1).cursor();
                getVariantProductDetailsByRefsMap(variantProductDetailsToRefMap, refList, catalogueRef, lastCursor);
            }
        }
        return variantProductDetailsToRefMap;
    }
}
