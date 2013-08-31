import sys
from pydev_console_utils import BaseInterpreterInterface
import re

# Uncomment to force PyDev standard shell.
# raise ImportError()

try:
    # Versions of IPython from 0.11 were designed to integrate into tools other
    # that IPython's application terminal frontend
    from pydev_ipython_console_011 import PyDevFrontEnd
except ImportError:
    # Prior to 0.11 we need to be clever about the integration, however this leaves
    # many parts of IPython not fully working
    from pydev_ipython_console_010 import PyDevFrontEnd


#=======================================================================================================================
# InterpreterInterface
#=======================================================================================================================
class InterpreterInterface(BaseInterpreterInterface):
    '''
        The methods in this class should be registered in the xml-rpc server.
    '''

    def __init__(self, host, client_port):
        self.client_port = client_port
        self.host = host
        self.interpreter = PyDevFrontEnd()
        self._input_error_printed = False


    def doAddExec(self, line):
        return bool(self.interpreter.addExec(line))


    def getNamespace(self):
        return self.interpreter.getNamespace()


    def getCompletions(self, text, act_tok):
        try:
            ipython_completion = text.startswith('%')
            if not ipython_completion:
                s = re.search(r'\bcd\b', text)
                if s is not None and s.start() == 0:
                    ipython_completion = True

            if ipython_completion:
                TYPE_LOCAL = '9'
                _line, completions = self.interpreter.complete(text)

                ret = []
                append = ret.append
                for completion in completions:
                    append((completion, '', '', TYPE_LOCAL))
                return ret

            # Otherwise, use the default PyDev completer (to get nice icons)
            from _pydev_completer import Completer
            completer = Completer(self.getNamespace(), None)
            return completer.complete(act_tok)
        except:
            import traceback;traceback.print_exc()
            return []


    def close(self):
        sys.exit(0)

