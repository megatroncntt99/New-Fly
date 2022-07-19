package com.vannv.train.newsfly.presentation.person

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.vannv.train.newsfly.databinding.FragmentPersonBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.utils.LogCat
import com.vannv.train.newsfly.utils.launchWhenCreated
import dagger.hilt.android.AndroidEntryPoint


/**
 * Author: vannv8@fpt.com.vn
 * Date: 06/07/2022
 */

@AndroidEntryPoint
class PersonFragment : BaseFragment<FragmentPersonBinding>() {

    private val viewModel: PersonViewModel by viewModels()
    private val adapter: ListPersonAdapter by lazy {
        ListPersonAdapter(onItemClick = {
            viewModel.getPersonById(it.id)
        }, onClickDeleteItem = {
            viewModel.onDeletePerson(it.id)
        })
    }

    override fun getViewBinding() = FragmentPersonBinding.inflate(layoutInflater)

    override fun setupUI() {
        getVB().rvPerson.adapter = adapter
        getVB().setOnClickSubmit {
            viewModel.onInsertPerson(firstName = getVB().edtFirstName.text.toString(), lastName = getVB().edtLastName.text.toString())
        }
    }

    override fun setupVM() {
        launchWhenCreated {
            viewModel.persons.collect {
                adapter.submitList(it)
            }
        }
        launchWhenCreated {
            viewModel.personDetail.collect {
                LogCat.d(it?.firstName.orEmpty())
            }
        }
    }

}