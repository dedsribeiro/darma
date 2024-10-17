package com.darma.darma;

/**
 * Created by Danilo on 07/05/2017.
 */

public class Config {

    public static final String URL_ADD="http://darmacoleta.engenharia.ws/addAdm.php";
    public static final String URL_ADD_FUN="http://darmacoleta.engenharia.ws/addFuncionario.php";
    public static final String URL_ADD_COLETA="http://darmacoleta.engenharia.ws/addColeta.php";
    public static final String URL_GET_ALL="http://darmacoleta.engenharia.ws/listColeta.php";
    public static final String URL_GET_ALL_CLOSED="http://darmacoleta.engenharia.ws/listColetaFechada.php";
    public static final String URL_GET_EMP="http://darmacoleta.engenharia.ws/getColeta.php";
    public static final String URL_GET_CLOSED="http://darmacoleta.engenharia.ws/getColetaFechada.php";
    public static final String URL_UPDATE_EMP="http://darmacoleta.engenharia.ws/updateColeta.php";




    //KEYS PARA ADM
    public static final String KEY_ADM_NOME = "nome";
    public static final String KEY_ADM_CPF = "cpf";
    public static final String KEY_ADM_SENHA = "senha";
    //KEYS PARA FUN
    public static final String KEY_NOME_FUN = "nome";
    public static final String KEY_TELEFONE_FUN = "telefone";
    public static final String KEY_USUARIO_FUN = "usuario";
    public static final String KEY_SENHA_FUN = "senha";
    //KEYS PARA COLETA
    public static final String KEY_ID_COLETA = "id";
    public static final String KEY_NOME_COLETA = "nome";
    public static final String KEY_TELEFONE_COLETA = "telefone";
    public static final String KEY_ENDERECO_COLETA = "endereco";
    public static final String KEY_DT_COLETA= "dt_coleta";
    public static final String KEY_DT_RETIRADA= "dt_retirada";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NOME = "nome";
    public static final String TAG_STATUS = "status";
    public static final String TAG_TELEFONE = "telefone";
    public static final String TAG_ENDERECO = "endereco";
    public static final String TAG_DATA_COLETA = "dt_coleta";
    public static final String TAG_DATA_RETIRADA = "dt_recolhida";

    //employee id to pass with intent
    //public static final String EMP_ID = "emp_id";
    public static final String EMP_ID = "emp_id";

}
