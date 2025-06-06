using Microsoft.EntityFrameworkCore;
using AbrigoApi.Context; // <- Coloque o namespace onde está seu DbContext

var builder = WebApplication.CreateBuilder(args);
//liberar cors para meu mobile
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowLocalhost",
        policy =>
        {
            policy.WithOrigins("http://192.168.0.10:8081") // ? Aqui é onde seu app React Native roda
                  .AllowAnyHeader()
                  .AllowAnyMethod()
                  .AllowCredentials(); // se precisar de cookies ou auth headers
        });
});

// Adiciona o DbContext com Oracle
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseOracle(builder.Configuration.GetConnectionString("DefaultConnection")));

// Add services to the container.
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
app.UseCors("AllowLocalhost");
app.UseHttpsRedirection();
app.UseAuthorization();
app.MapControllers();
app.Run();
