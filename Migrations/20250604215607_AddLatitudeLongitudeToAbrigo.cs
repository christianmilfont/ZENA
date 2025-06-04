using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace AbrigoApi.Migrations
{
    /// <inheritdoc />
    public partial class AddLatitudeLongitudeToAbrigo : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "LATITUDE",
                table: "ABRIGOS",
                type: "NUMBER(9,6)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "LONGITUDE",
                table: "ABRIGOS",
                type: "NUMBER(9,6)",
                nullable: false,
                defaultValue: 0m);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "LATITUDE",
                table: "ABRIGOS");

            migrationBuilder.DropColumn(
                name: "LONGITUDE",
                table: "ABRIGOS");
        }
    }
}
