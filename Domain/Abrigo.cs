namespace AbrigoApi.Domain
{
    public class Abrigo
    {

        public String Id { get; set; }
        public string Nome { get; set; } = string.Empty;
        public string Endereco { get; set; } = string.Empty;
        public int Capacidade { get; set; }
        public int OcupacaoAtual { get; set; }
        public bool Ativo { get; set; } 

        public String UsuarioId { get; set; }
        public Usuario Usuario { get; set; } = null!;
        public Abrigo()
        {
            Id = Guid.NewGuid().ToString(); // Id gerado assim que o objeto é criado
        }
    }

}
