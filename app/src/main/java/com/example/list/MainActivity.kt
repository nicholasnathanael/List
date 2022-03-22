package com.example.list

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import com.example.list.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    companion object {
        const val BASE_URL = "https://reqres.in"
    }

    private var compositeDisposable = CompositeDisposable()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainInterface: MainInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MainInterface::class.java)
        populateUserList(mainInterface, false)

        binding.swipeRefresh.setOnRefreshListener {
            populateUserList(mainInterface, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun populateUserList(mainInterface: MainInterface, isRefreshed: Boolean) {
        val disposable = mainInterface.getTotalUser()
            .flatMap { userListResponse ->
                return@flatMap mainInterface.getUserList(1, userListResponse.total)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { userListResponse ->
                val users = userListResponse.userList
                val adapter = UserListAdapter(this, users)
                binding.userList.adapter = adapter
                binding.userList.layoutManager = LinearLayoutManager(applicationContext)
                if (isRefreshed) {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        compositeDisposable.add(disposable)
    }
}