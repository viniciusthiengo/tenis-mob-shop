package thiengo.com.br.tnismobshop

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import thiengo.com.br.tnismobshop.databinding.SneakerBinding
import thiengo.com.br.tnismobshop.domain.Sneaker

class SneakersAdapter(
        private val context: Context,
        private val sneakers: List<Sneaker> ) :
        RecyclerView.Adapter<SneakersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int ) : SneakersAdapter.ViewHolder {

        val layoutInflater = LayoutInflater
                .from( context )

        /*
         * Vinculando o adapter a classe de binding do layout
         * de item.
         * */
        val sneakerBinding = SneakerBinding
                .inflate( layoutInflater, parent, false )

        return ViewHolder( sneakerBinding )
    }

    override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int ) {

        holder.setData( sneakers[ position ] )
    }

    override fun getItemCount(): Int {
        return sneakers.size
    }

    inner class ViewHolder( val binding: SneakerBinding ) :
            RecyclerView.ViewHolder( binding.root ),
            View.OnClickListener {

        init {
            /*
             * A propriedade root contém exatamente o
             * ViewGroup container do layout de item.
             * O uso do método setOnClickListener() foi
             * mantido, pois como em outras partes do
             * projeto, o vinculo de listener utilizando
             * métodos direto dos objetos de visualização
             * é mais simples do que quando utilizando
             * a sintaxe Data Binding.
             * */
            binding.root.setOnClickListener( this )
        }

        fun setData( sneaker: Sneaker ) {
            binding.sneaker = sneaker

            /*
             * O método executePendingBindings() força o
             * binding a ser executado imediatamente, em
             * vez de atrasá-las até o próximo quadro.
             * */
            binding.executePendingBindings()
        }

        override fun onClick( view: View ) {
            val intent = Intent( context, SneakerDetailsActivity::class.java )

            intent.putExtra( Sneaker.KEY, sneakers[ adapterPosition ] )
            context.startActivity( intent )
        }
    }
}