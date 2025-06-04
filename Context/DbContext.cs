using Microsoft.EntityFrameworkCore;
using AbrigoApi.Domain;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace AbrigoApi.Context
{
    public class AppDbContext : DbContext
    {
        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<Abrigo> Abrigos { get; set; }

        public AppDbContext(DbContextOptions<AppDbContext> options)
            : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            // Configuração da entidade Usuario
            modelBuilder.Entity<Usuario>(entity =>
            {
                entity.ToTable("USUARIOS");

                entity.HasKey(u => u.Id)
                      .HasName("PK_USUARIOS");

                    entity.Property(u => u.Id)
                      .HasColumnName("ID")
                      .HasColumnType("VARCHAR2(255)")
                      .IsRequired();

                entity.Property(u => u.Username)
                      .HasColumnName("USERNAME")
                      .HasMaxLength(100)
                      .IsRequired();

                entity.Property(u => u.Password)
                      .HasColumnName("PASSWORD")
                      .HasMaxLength(255)
                      .IsRequired();

                entity.Property(u => u.Email)
                      .HasColumnName("EMAIL")
                      .HasMaxLength(100)
                      .IsRequired();

                entity.Property(u => u.Role)
                      .HasColumnName("ROLE")
                      .HasMaxLength(50);

                // Excluir Usuario das migrations se quiser manter tabela manualmente
                entity.Metadata.SetIsTableExcludedFromMigrations(true);

                entity.HasMany(u => u.Abrigos)
                      .WithOne(a => a.Usuario)
                      .HasForeignKey(a => a.UsuarioId)
                      .HasConstraintName("FK_ABRIGOS_USUARIOS_USUARIOID")
                      .OnDelete(DeleteBehavior.Cascade);
            });

            // Configuração da entidade Abrigo
            modelBuilder.Entity<Abrigo>(entity =>
            {
                entity.ToTable("ABRIGOS");

                entity.HasKey(a => a.Id)
                      .HasName("PK_ABRIGOS");

                entity.Property(a => a.Id)
                     .HasColumnName("ID")
                     .HasColumnType("VARCHAR2(255)")
                     .IsRequired();

                entity.Property(a => a.Nome)
                      .HasColumnName("NOME")
                      .HasMaxLength(100)
                      .IsRequired();

                entity.Property(a => a.Endereco)
                      .HasColumnName("ENDERECO")
                      .HasMaxLength(255)
                      .IsRequired();

                entity.Property(a => a.Capacidade)
                      .HasColumnName("CAPACIDADE")
                      .HasColumnType("NUMBER(10)")
                      .IsRequired();

                entity.Property(a => a.OcupacaoAtual)
                      .HasColumnName("OCUPACAO_ATUAL")
                      .HasColumnType("NUMBER(10)")
                      .IsRequired();

             

                entity.Property(a => a.UsuarioId)
                      .HasColumnName("USUARIO_ID")
                      .HasMaxLength(255)
                      .IsRequired();
                entity.Property(a => a.Latitude)
                      .HasColumnName("LATITUDE")
                      .HasColumnType("NUMBER(9,6)")
                      .IsRequired();

                entity.Property(a => a.Longitude)
                      .HasColumnName("LONGITUDE")
                      .HasColumnType("NUMBER(9,6)")
                      .IsRequired();

                entity.HasOne(a => a.Usuario)
                      .WithMany(u => u.Abrigos)
                      .HasForeignKey(a => a.UsuarioId)
                      .HasConstraintName("FK_ABRIGOS_USUARIOS_USUARIOID")
                      .OnDelete(DeleteBehavior.Cascade);
            });
        }
    }
}
