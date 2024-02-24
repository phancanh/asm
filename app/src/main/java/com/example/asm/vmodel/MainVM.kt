package com.example.asm.vmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asm.model.KeyValue
import com.example.asm.model.ObjRestaurant

class MainVM : ViewModel() {
    private val _liveIndexStack = MutableLiveData<Int>(0)
    private val _amout = MutableLiveData<Int>(0)
    private val _obj = MutableLiveData<ObjRestaurant>()
    private val _nameRestaurant = MutableLiveData<List<String>>()
    private val _mList = MutableLiveData<List<KeyValue>>()
    private val _medal = MutableLiveData<String>()
    private val _name = MutableLiveData<String>()
    private val _nameDish = MutableLiveData<List<String>>()
    val indexStack: LiveData<Int> get() = _liveIndexStack
    val mList: LiveData<List<KeyValue>> get() = _mList
    val amout: LiveData<Int> get() = _amout
    val medal: LiveData<String> get() = _medal
    val nameRestaurant: LiveData<List<String>> get() = _nameRestaurant
    val nameDish: LiveData<List<String>> get() = _nameDish
    val name: LiveData<String> get() = _name
    val obj: LiveData<ObjRestaurant> get() = _obj


    fun setIndex(index: Int) {
        _liveIndexStack.value = index
    }

    fun setAmout(index: Int) {
        _amout.value = index
    }

    fun setListTotal(lst:List<KeyValue>) {
        _mList.value = lst
    }

    fun setObj(json: ObjRestaurant) {
        _obj.value = json
    }

    fun setMedal(json: String) {
        _medal.value = json
    }

    fun setListName(data: List<String>) {
        _nameRestaurant.value = data
    }
    fun setName(data: String) {
        _name.value=data
    }

    fun setDish(data: String) {

        var list: MutableList<String> = mutableListOf()
        obj.value?.dishes?.forEach {
            if (data == it.restaurant) {
                list.add(it.name)
            }
        }
        _nameDish.value = list
    }
}