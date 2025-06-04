using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace AbrigoApi.Migrations
{
    /// <inheritdoc />
    public partial class CreateAbrigosTable : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "ABRIGOS",
                columns: table => new
                {
                    ID = table.Column<string>(type: "VARCHAR2(255)", nullable: false),
                    NOME = table.Column<string>(type: "NVARCHAR2(100)", maxLength: 100, nullable: false),
                    ENDERECO = table.Column<string>(type: "NVARCHAR2(255)", maxLength: 255, nullable: false),
                    CAPACIDADE = table.Column<int>(type: "NUMBER(10)", nullable: false),
                    OCUPACAO_ATUAL = table.Column<int>(type: "NUMBER(10)", nullable: false),
                    ATIVO = table.Column<byte>(type: "NUMBER(1)", nullable: false),
                    USUARIO_ID = table.Column<string>(type: "VARCHAR2(255)", maxLength: 255, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ABRIGOS", x => x.ID);
                    table.ForeignKey(
                        name: "FK_ABRIGOS_USUARIOS_USUARIOID",
                        column: x => x.USUARIO_ID,
                        principalTable: "USUARIOS",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ABRIGOS_USUARIO_ID",
                table: "ABRIGOS",
                column: "USUARIO_ID");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ABRIGOS");
        }
    }
}
