using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;


namespace TextAdventureAdminWPF
{
    public class StatusDTO
    {
        [JsonPropertyName("id")]
        public long Id { get; set; }


        [JsonPropertyName("status")]
        public bool Status { get; set; }
    }
}


