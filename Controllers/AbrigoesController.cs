using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using AbrigoApi.Context;
using AbrigoApi.Domain;

namespace AbrigoApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AbrigoesController : ControllerBase
    {
        private readonly AppDbContext _context;

        public AbrigoesController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/Abrigoes
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Abrigo>>> GetAbrigos()
        {
            return await _context.Abrigos.ToListAsync();
        }

        // GET: api/Abrigoes/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Abrigo>> GetAbrigo(string id)
        {
            var abrigo = await _context.Abrigos.FindAsync(id);

            if (abrigo == null)
            {
                return NotFound();
            }

            return abrigo;
        }

        // PUT: api/Abrigoes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutAbrigo(string id, Abrigo abrigo)
        {
            if (id != abrigo.Id)
            {
                return BadRequest();
            }

            _context.Entry(abrigo).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!AbrigoExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Abrigoes
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Abrigo>> PostAbrigo(Abrigo abrigo)
        {
            _context.Abrigos.Add(abrigo);
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (AbrigoExists(abrigo.Id))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtAction("GetAbrigo", new { id = abrigo.Id }, abrigo);
        }

        // DELETE: api/Abrigoes/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteAbrigo(string id)
        {
            var abrigo = await _context.Abrigos.FindAsync(id);
            if (abrigo == null)
            {
                return NotFound();
            }

            _context.Abrigos.Remove(abrigo);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool AbrigoExists(string id)
        {
            return _context.Abrigos.Any(e => e.Id == id);
        }
    }
}
