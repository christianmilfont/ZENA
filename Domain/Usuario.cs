namespace AbrigoApi.Domain
{
    public class Usuario
    {
        public String Id { get; set; } 

        public string Username { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
        public string Role { get; set; } = "user";

        public ICollection<Abrigo> Abrigos { get; set; } = new List<Abrigo>();
        public Usuario()
        {
            Id = Guid.NewGuid().ToString(); // Já garante um Id não nulo ao criar
        }
    }

}
