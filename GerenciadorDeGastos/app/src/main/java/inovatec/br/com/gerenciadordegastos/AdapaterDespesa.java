package inovatec.br.com.gerenciadordegastos;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gustavo on 28/10/2016.
 */
public class AdapaterDespesa extends RecyclerView.Adapter<AdapaterDespesa.MyViewHolder> {

    private List<Despesa> list;

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public TextView descricao, tipo, valor;

        public MyViewHolder(View view) {
            super(view);
            descricao = (TextView) view.findViewById(R.id.descricao);
            tipo = (TextView) view.findViewById(R.id.tipo);
            valor = (TextView) view.findViewById(R.id.valor);
        }


    }


    public AdapaterDespesa(List<Despesa> list) {
        this.list = list;
    }
    public void addItem(final Despesa item) {
        this.list.add(item);
        //Atualizar a lista caso seja adicionado algum item
        notifyDataSetChanged();
    }
    public void removeItem(int pos){
        this.list.remove(pos);
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_despesa, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Despesa d = list.get(position);
        holder.descricao.setText(d.getDescricao());
        holder.tipo.setText("");
        holder.valor.setText("" + d.getValor());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
