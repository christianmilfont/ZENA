using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace AbrigoApi.Migrations
{
    /// <inheritdoc />
    public partial class AtualizandoMeuBancoDomains : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ATIVO",
                table: "ABRIGOS");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "ATIVO",
                table: "ABRIGOS",
                type: "NUMBER(1)",
                nullable: false,
                defaultValue: false);
        }
    }
}
