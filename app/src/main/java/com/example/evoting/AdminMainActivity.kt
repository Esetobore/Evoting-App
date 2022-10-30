package com.example.evoting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

class AdminMainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: MeowBottomNavigation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)


        addFragment(AdminVoterControl())
        bottomNavigation.show(0)
        bottomNavigation.add(MeowBottomNavigation.Model(0,R.drawable.ic_voters_idss))
        bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.ic_candidate))
        bottomNavigation.add(MeowBottomNavigation.Model(2,R.drawable.ic_createelection))
        bottomNavigation.add(MeowBottomNavigation.Model(3,R.drawable.ic_resultelection))

        bottomNavigation.setOnClickMenuListener {

            when(it.id){
                0 -> {
                    replaceFragment(AdminVoterControl.newInstance())
                }
                1 -> {
                    replaceFragment(AdminCandidateControl.newInstance())
                }
                2 -> {
                    replaceFragment(CreateElection.newInstance())
                }
                3 -> {
                    replaceFragment(ElectionResult.newInstance())
                }
                else->{
                    replaceFragment(AdminVoterControl.newInstance())
                }

            }
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_layout,fragment)
            .addToBackStack(fragment::class.java.simpleName).commit()
    }
    private fun addFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragment_layout,fragment)
            .addToBackStack(fragment::class.java.simpleName).commit()
    }

}
