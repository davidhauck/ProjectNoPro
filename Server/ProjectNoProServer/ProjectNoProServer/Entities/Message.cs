﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ProjectNoProServer.Entities
{
    public class Message
    {
        public string Title { get; set; }
        public string Text { get; set; }
    }
}