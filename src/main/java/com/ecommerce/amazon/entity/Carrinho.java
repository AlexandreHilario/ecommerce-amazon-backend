importar  Jacarta . persistência .*;
import  lombok .*;

import  java.time.LocalDateTime ;​​​​
import  java.util.ArrayList ;​​​​
import  java.util.List ;​​​​

@ Entidade
@ Mesa ( nome = "carrinhos" )
@ Getter
@ Setter
@ NoArgsConstructor
@ AllArgsConstructor
@ Construtor
public  class  Carrinho {

    @ Eu ia
    @GeneratedValue ( strategy = GenerationType.IDENTITY )​​​
     ID longo  privado ;

    @OneToOne ( fetch = FetchType.LAZY , optional = false )​​​
    @JoinColumn ( name = "usuario_id " , nullable = false , unique = true ,
                ForeignKey = @ ForeignKey ( nome = "fk_carrinho_usuario" ))
     usuário  privado usuário ;

    @ Coluna ( nome = "criado_em" , inserível = falso , atualizável = falso )
    private  LocalDateTime  criadoEm ;

    @OneToMany ( mappedBy = " carrinho " , cascade = CascadeType.ALL , orphanRemoval = true )​
    @ Construtor . Padrão
     lista privada < CarrinhoProduto > itens = new ArrayList <>() ; 
}