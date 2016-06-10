using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ProjectNoProServer.Entities
{
    public class Message
    {
        [Key]
        public int Id { get; set; }
        public string Value { get; set; }
    }
}