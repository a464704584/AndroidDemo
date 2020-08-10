package com.cy.demo.base;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

/**
 * @创建者 CY
 * @创建时间 2020/8/4 13:07
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public abstract class BaseFragment extends BaseMainFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                NavHostFragment.findNavController(BaseFragment.this)
                        .popBackStack();
            }
        };




        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}