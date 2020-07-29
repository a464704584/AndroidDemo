package com.cy.demo.databind;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cy.demo.BR;
import com.cy.demo.bean.ModuleItem;
import com.cy.demo.databinding.ItemModuleBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @创建者 CY
 * @创建时间 2020/7/29 13:15
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class BaseBindAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    protected int mLayoutId;
    protected List<T> mData;

    public BaseBindAdapter(final int layoutId, List<T> list) {
        super(layoutId, list);
        mLayoutId = layoutId;
        mData = list;
    }

    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);

    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, T t) {
        ViewDataBinding binding=baseViewHolder.getBinding();
        if (binding!=null){
            binding.setVariable(BR.item,t);
        }
    }
}