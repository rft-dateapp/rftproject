using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Pub_n_Fun.Error
{
    internal class InvalidTokenException : SystemException
    {
        internal InvalidTokenException(string message) : base(message)
        {

        }
    }
}