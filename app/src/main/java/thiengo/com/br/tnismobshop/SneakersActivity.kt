package thiengo.com.br.tnismobshop

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_sneakers.*
import kotlinx.android.synthetic.main.app_bar_sneakers.*
import thiengo.com.br.tnismobshop.data.Database

class SneakersActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sneakers)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        initHeader()
        initSneakers()
    }

    private fun initSneakers(){
        rv_sneakers.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        rv_sneakers.layoutManager = layoutManager

        rv_sneakers.adapter = SneakersAdapter(this, Database.getSneakers(this))
    }

    private fun initHeader(){
        val user = Database.getUser()
        val viewRoot = nav_view.getHeaderView(0) as ViewGroup

        val ivProfile = viewRoot.getChildAt(0) as ImageView
        val tvName = viewRoot.getChildAt(1) as TextView
        val tvEmail = viewRoot.getChildAt(2) as TextView

        ivProfile.setImageResource( user.image )
        tvName.text = user.name
        tvEmail.text = user.email
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }

    /*
     * Somente para apresentarmos os itens de "buscar" e
     * "carrinho de compras"
     * */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tenis_list, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)

        /*
         * Para este aplicativo de exemplo, manterá sempre
         * o mesmo item selecionado.
         * */
        return false
    }
}
