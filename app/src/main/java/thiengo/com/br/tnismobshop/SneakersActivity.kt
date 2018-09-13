package thiengo.com.br.tnismobshop

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_sneakers.*
import kotlinx.android.synthetic.main.app_bar_sneakers.*
import thiengo.com.br.tnismobshop.data.Database
import thiengo.com.br.tnismobshop.databinding.NavHeaderSneakerBinding

class SneakersActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_sneakers )
        setSupportActionBar( toolbar )

        val toggle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener( toggle )
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener( this )

        initHeader()
        initSneakers()
    }

    private fun initSneakers(){
        rv_sneakers.setHasFixedSize( true )

        val layoutManager = LinearLayoutManager( this )
        rv_sneakers.layoutManager = layoutManager

        rv_sneakers.adapter = SneakersAdapter( this, Database.getSneakers( this ) )
    }

    private fun initHeader(){
        val user = Database.getUser()

        /*
         * Realizando o bind somente do layout de cabeçalho
         * do menu gaveta.
         * */
        val navHeader = NavHeaderSneakerBinding
                .bind( nav_view.getHeaderView(0) )
        navHeader.user = user
    }

    override fun onBackPressed() {
        if ( drawer_layout.isDrawerOpen( GravityCompat.START ) ) {
            drawer_layout.closeDrawer( GravityCompat.START )
        }
        else {
            super.onBackPressed()
        }
    }

    /*
     * Somente para apresentarmos os itens de "buscar" e
     * "carrinho de compras"
     * */
    override fun onCreateOptionsMenu( menu: Menu ): Boolean {
        menuInflater.inflate( R.menu.tenis_list, menu )
        return true
    }

    override fun onNavigationItemSelected( item: MenuItem ): Boolean {
        drawer_layout.closeDrawer( GravityCompat.START )

        /*
         * Para este aplicativo de exemplo, manterá sempre
         * o mesmo item selecionado.
         * */
        return false
    }
}
