/**
 * Opções de configuração do JiiStore
 * 
 * - key: string = 'id';
 * - endpoints: JiiStoreEndpoints
 * - loadByGet: boolean = false;
 * 
 * Apenas endpoints é obrigatório
 */
export class JiiStoreOptions {

  /**
   * *Opcional*
   * 
   * Chave que será usada na JiiStore, que definirá qual atributo
   * do objeto será enviado no request do byKey
   * 
   * Se não definida (key = undefined) a JiiStore condiderá o valor
   * padrão ('id'). Para tratar todo o Objeto como a Chave, e o
   * retornar no byKey, deve-se atribuir à key o valor null
   * 
   * *Dica: setar a key como null para uso em header filter,*
   * *pois ele só precisa do load, e a key causa comportamentos*
   * *estranhos nele*
   */
  key?: string = 'id';

  endpoints: JiiStoreEndpoints = {};

  /**
   * *Opcional*
   * 
   * Flag que indica se o endpoint do load é apenas um GET simples
   * (não faz envio de JiiPayload)
   * 
   * Se não definido, o load será feito com o método POST,
   * enviando JiiPayload como body
   */
  loadByGet?: boolean = false;

}

export class JiiStoreEndpoints {
  /**
   * Fragmento de URL que deve ser usado para realizar o request do byKey
   * 
   * Se não definido o byKey usa o endpoint ${path}/${key}
   */
  byKey?: string;
  /**
   * Fragmento de URL que deve ser usado para realizar o request do load
   * 
   * Se não definido o load usa o endpoint ${path}/jii
   */
  load?: string;
}

